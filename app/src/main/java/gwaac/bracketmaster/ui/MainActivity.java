package gwaac.bracketmaster.ui;

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
import android.view.View;

import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;
import gwaac.bracketmaster.BracketMasterApplication;
import gwaac.bracketmaster.data.helper.DataManager;
import gwaac.bracketmaster.R;
import gwaac.bracketmaster.data.adapter.TournamentAdapter;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tournament_recycler_view) RecyclerView mRecyclerView;

    @Bind(R.id.transparent_overlay) View mOverlay;
    private boolean mOverlayVisible;

    @Bind(R.id.fab_menu) FloatingActionButton mFabMenu;
    @Bind(R.id.fab_search) FloatingActionButton mFabSearch;
    @Bind(R.id.fab_new) FloatingActionButton mFabNew;
    @Bind(R.id.fab_logout) FloatingActionButton mFabLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_main);

        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        TournamentAdapter mAdapter = new TournamentAdapter(this, new DataManager(this).getTournamentData());
        mRecyclerView.setAdapter(mAdapter);

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
