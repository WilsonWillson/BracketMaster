package gwaac.bracketmaster;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private View mOverlay;
    private boolean mOverlayVisible;

    private MainActivity mThisActivity;

    private FloatingActionButton mFabMenu;
    private FloatingActionButton mFabSearch;
    private FloatingActionButton mFabNew;
    private FloatingActionButton mFabLogout;

    private Animator mRotateClockwiseAnim;
    private Animator mRotateCounterClockwiseAnim;
    private Animator mTranslateUpAnim;
    private Animator mTranslateDownAnim;
    private Animator mTranslateLeftAnim;
    private Animator mTranslateRightAnim;
    private Animator mTranslateLeftUpAnim;
    private Animator mTranslateRightDownAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_main);

        mThisActivity = this;

        mRecyclerView = (RecyclerView)findViewById(R.id.tournament_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TournamentAdapter(new Tournament(this).getSampleData());
        mRecyclerView.setAdapter(mAdapter);

        mOverlay = findViewById(R.id.transparent_overlay);
        mOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentOverlay();
            }
        });
        mOverlayVisible = false;

        mFabMenu = (FloatingActionButton) findViewById(R.id.fab_menu);
        mFabMenu.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        mFabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //segueToCreation();
                presentOverlay();
            }
        });

        mFabSearch = (FloatingActionButton)findViewById(R.id.fab_search);
        mFabSearch.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        mFabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segueToSearch();
            }
        });

        mFabNew = (FloatingActionButton)findViewById(R.id.fab_new);
        mFabNew.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        mFabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segueToCreation();
            }
        });

        mFabLogout = (FloatingActionButton)findViewById(R.id.fab_logout);
        mFabLogout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGrey)));
        mFabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(mThisActivity)
                        .setTitle("Logout from BracketMaster")
                        .setMessage("Would you like to log out?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
            preferences.edit().putString("uid", uid);
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
        segue(SearchActivity.class);
    }

    private void logout() {
        Firebase ref = ((BracketMasterApplication)getApplication()).myFirebaseRef;
        ref.unauth();
        segueToLogin();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void presentOverlay() {
        if (mOverlayVisible) {
            mTranslateDownAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_down);
            mTranslateDownAnim.setTarget(mFabSearch);
            mTranslateDownAnim.start();

            mTranslateRightDownAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_down_right);
            mTranslateRightDownAnim.setTarget(mFabNew);
            mTranslateRightDownAnim.start();

            mTranslateRightAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_right);
            mTranslateRightAnim.setTarget(mFabLogout);
            mTranslateRightAnim.start();

            mRotateCounterClockwiseAnim = AnimatorInflater.loadAnimator(this, R.animator.rotate_counterclockwise);
            mRotateCounterClockwiseAnim.setTarget(mFabMenu);
            mRotateCounterClockwiseAnim.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mOverlay.setVisibility(View.GONE);
                }
            }, 200);

            mOverlayVisible = false;
        } else {
            mOverlayVisible = true;
            mOverlay.setVisibility(View.VISIBLE);

            mRotateClockwiseAnim = AnimatorInflater.loadAnimator(this, R.animator.rotate_clockwise);
            mRotateClockwiseAnim.setTarget(mFabMenu);
            mRotateClockwiseAnim.start();

            mTranslateLeftAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_left);
            mTranslateLeftAnim.setTarget(mFabLogout);
            mTranslateLeftAnim.start();

            mTranslateLeftUpAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_up_left);
            mTranslateLeftUpAnim.setTarget(mFabNew);
            mTranslateLeftUpAnim.start();

            mTranslateUpAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_up);
            mTranslateUpAnim.setTarget(mFabSearch);
            mTranslateUpAnim.start();
        }
    }
}
