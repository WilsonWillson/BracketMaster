package gwaac.bracketmaster.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import gwaac.bracketmaster.BracketMasterApplication;
import gwaac.bracketmaster.data.helper.TournamentProperties;
import gwaac.bracketmaster.data.helper.DataManager;
import gwaac.bracketmaster.R;
import gwaac.bracketmaster.data.adapter.TournamentAdapter;
import gwaac.bracketmaster.data.helper.TournamentSuggestionProvider;
import gwaac.bracketmaster.data.model.Tournament;
import gwaac.bracketmaster.ui.modal.Notifier;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tournament_recycler_view) RecyclerView mRecyclerView;
    private RecyclerView.Adapter mTournamentAdapter;
    @Bind(R.id.transparent_overlay) View mOverlay;
    @Bind(R.id.search_no_results_label) TextView mNoResultsLabel;
    private boolean mOverlayVisible;

    private static boolean madeDataFlow;

    @Bind(R.id.fab_menu) FloatingActionButton mFabMenu;
    @Bind(R.id.fab_search) FloatingActionButton mFabSearch;
    @Bind(R.id.fab_new) FloatingActionButton mFabNew;
    @Bind(R.id.fab_logout) FloatingActionButton mFabLogout;

    private List<Tournament> mTournamentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mTournamentAdapter = new TournamentAdapter(this, DataManager.getTournamentData());
        mRecyclerView.setAdapter(mTournamentAdapter);

        mOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleOverlay();
            }
        });
        mOverlayVisible = false;

        mFabMenu.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        mFabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleOverlay();
            }
        });

        mFabSearch.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        mFabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segueToSearch();
            }
        });

        mFabNew.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        mFabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segueToCreation();
            }
        });

        mFabLogout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGrey)));
        mFabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Logout from BracketMaster?")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                toggleOverlay();
                                logout();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });

        loginIfNeeded();

        if (!madeDataFlow)
            makeDataFlow();
        madeDataFlow = true;

        Intent intent = getIntent();
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mRecyclerView.swapAdapter(mTournamentAdapter, true);
                return true;
            }
        });

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    TournamentSuggestionProvider.AUTHORITY, TournamentSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            List<Tournament> tournamentList = getSearchResults(query);
            if (tournamentList == null || tournamentList.size() == 0) {
                displayNoResults();
            } else {
                mNoResultsLabel.setVisibility(View.GONE);
                mTournamentList = tournamentList;
                TournamentAdapter newAdapter = new TournamentAdapter(this, mTournamentList);
                mRecyclerView.swapAdapter(newAdapter, false);
            }

        }

    }

    public List<Tournament> getSearchResults(String query) {
        Log.v("[Search Activity]", "Query: " + query);
        Set<Tournament> tournaments = new HashSet<>();
        tournaments.addAll(DataManager.searchByTitle(query));
        tournaments.addAll(DataManager.searchByOwner(query));
        tournaments.addAll(DataManager.searchByDescription(query));
        List<Tournament> result = new ArrayList<>();
        result.addAll(tournaments);
        Log.v("[Search Activity]", "Result size: " + result.size());
        return result;
    }

    public void displayNoResults() {
        mNoResultsLabel.setText("No Results found.\nPlease return to the search page and try again.");
        mNoResultsLabel.setVisibility(View.VISIBLE);
    }

    private void makeDataFlow() {
        Log.v("******************", "Adding another listener.");
        Firebase ref = ((BracketMasterApplication)getApplication()).myFirebaseRef;
        ref.child("tournaments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TournamentProperties tp = dataSnapshot.getValue(TournamentProperties.class);
                Tournament t = TournamentProperties.toTournament(tp);

                DataManager.getTournamentData().add(t);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void loginIfNeeded() {
        if (!loggedIn()) {
            segueToLogin();
        }
        else {
            guaranteeUIDStored();
        }
    }

    private void guaranteeUIDStored() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getString("uid", "null").equals("null")) {
            Firebase ref = ((BracketMasterApplication)getApplication()).myFirebaseRef;
            String uid = ref.getAuth().getUid();
            preferences.edit().putString("uid", uid).apply();
        }

    }

    private boolean loggedIn() {
        Firebase ref = ((BracketMasterApplication)getApplication()).myFirebaseRef;
        return ref.getAuth() != null;
    }

    private void segueToLogin() {
        segue(LoginActivity.class);
    }

    private void segue(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    private void segueToCreation() {
        segue(CreationActivity.class);
    }

    private void segueToSearch() {
        toggleOverlay();
        final Activity activity = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Notifier notifier = new Notifier(activity, mRecyclerView);
                notifier.setOnVisibilityChangedListener(new Notifier.OnVisibilityChangedListener() {
                    @Override
                    public void onVisibilityChanged(boolean isVisible) {
                        if (isVisible) {
                            mFabMenu.setEnabled(false);
                        } else {
                            mFabMenu.setEnabled(true);
                        }
                    }
                });
                notifier.alertWithConfirmation("Search activity has been removed. Replacing with user account page in the near future.");
            }
        }, 250);
    }

    private void logout() {
        Firebase ref = ((BracketMasterApplication)getApplication()).myFirebaseRef;
        ref.unauth();
        segueToLogin();
    }

    public void toggleOverlay() {
        if (mOverlayVisible) {
            Animator translateDownAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_down);
            translateDownAnim.setTarget(mFabSearch);
            translateDownAnim.start();

            Animator translateRightDownAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_down_right);
            translateRightDownAnim.setTarget(mFabNew);
            translateRightDownAnim.start();

            Animator translateRightAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_right);
            translateRightAnim.setTarget(mFabLogout);
            translateRightAnim.start();

            Animator rotateCounterClockwiseAnim = AnimatorInflater.loadAnimator(this, R.animator.rotate_counterclockwise);
            rotateCounterClockwiseAnim.setTarget(mFabMenu);
            rotateCounterClockwiseAnim.start();

            Animator fadeOutAnim = AnimatorInflater.loadAnimator(this, R.animator.fade_out);
            fadeOutAnim.setTarget(mOverlay);
            fadeOutAnim.start();

            mOverlayVisible = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!mOverlayVisible) {
                        mOverlay.setVisibility(View.GONE);
                        mFabLogout.setVisibility(View.GONE);
                        mFabNew.setVisibility(View.GONE);
                        mFabSearch.setVisibility(View.GONE);
                    }
                }
            }, 200);
        } else {
            mOverlayVisible = true;
            mOverlay.setVisibility(View.VISIBLE);
            mFabLogout.setVisibility(View.VISIBLE);
            mFabNew.setVisibility(View.VISIBLE);
            mFabSearch.setVisibility(View.VISIBLE);

            Animator fadeInAnim = AnimatorInflater.loadAnimator(this, R.animator.fade_in);
            fadeInAnim.setTarget(mOverlay);
            fadeInAnim.start();

            Animator rotateClockwiseAnim = AnimatorInflater.loadAnimator(this, R.animator.rotate_clockwise);
            rotateClockwiseAnim.setTarget(mFabMenu);
            rotateClockwiseAnim.start();

            Animator translateLeftAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_left);
            translateLeftAnim.setTarget(mFabLogout);
            translateLeftAnim.start();

            Animator translateLeftUpAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_up_left);
            translateLeftUpAnim.setTarget(mFabNew);
            translateLeftUpAnim.start();

            Animator mFadeInAnim = AnimatorInflater.loadAnimator(this, R.animator.fade_in);
            mFadeInAnim.setTarget(mOverlay);
            mFadeInAnim.start();

            Animator mRotateClockwiseAnim = AnimatorInflater.loadAnimator(this, R.animator.rotate_clockwise);
            mRotateClockwiseAnim.setTarget(mFabMenu);
            mRotateClockwiseAnim.start();

            Animator mTranslateLeftAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_left);
            mTranslateLeftAnim.setTarget(mFabLogout);
            mTranslateLeftAnim.start();

            Animator mTranslateLeftUpAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_up_left);
            mTranslateLeftUpAnim.setTarget(mFabNew);
            mTranslateLeftUpAnim.start();

            Animator mTranslateUpAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_up);
            mTranslateUpAnim.setTarget(mFabSearch);
            mTranslateUpAnim.start();
        }
    }
}
