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
import android.view.View;

import com.firebase.client.Firebase;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tournament_recycler_view) RecyclerView mRecyclerView;

    @InjectView(R.id.transparent_overlay) View mOverlay;
    private boolean mOverlayVisible;

<<<<<<< Updated upstream
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
    private Animator mFadeInAnim;
    private Animator mFadeOutAnim;
=======
    @InjectView(R.id.fab_menu) FloatingActionButton mFabMenu;
    @InjectView(R.id.fab_search) FloatingActionButton mFabSearch;
    @InjectView(R.id.fab_new) FloatingActionButton mFabNew;
    @InjectView(R.id.fab_logout) FloatingActionButton mFabLogout;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_main);

        ButterKnife.inject(this);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new TournamentAdapter(new Tournament(this).getSampleData());
        mRecyclerView.setAdapter(mAdapter);

        mOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentOverlay();
            }
        });
        mOverlayVisible = false;

        mFabMenu.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        mFabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentOverlay();
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

    public void presentOverlay() {
        if (mOverlayVisible) {
            Animator mTranslateDownAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_down);
            mTranslateDownAnim.setTarget(mFabSearch);
            mTranslateDownAnim.start();

            Animator mTranslateRightDownAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_down_right);
            mTranslateRightDownAnim.setTarget(mFabNew);
            mTranslateRightDownAnim.start();

            Animator mTranslateRightAnim = AnimatorInflater.loadAnimator(this, R.animator.translate_right);
            mTranslateRightAnim.setTarget(mFabLogout);
            mTranslateRightAnim.start();

            Animator mRotateCounterClockwiseAnim = AnimatorInflater.loadAnimator(this, R.animator.rotate_counterclockwise);
            mRotateCounterClockwiseAnim.setTarget(mFabMenu);
            mRotateCounterClockwiseAnim.start();

            mFadeOutAnim = AnimatorInflater.loadAnimator(this, R.animator.fade_out);
            mFadeOutAnim.setTarget(mOverlay);
            mFadeOutAnim.start();

            mOverlayVisible = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!mOverlayVisible) {
                        mOverlay.setVisibility(View.GONE);
                    }
                }
            }, 200);
        } else {
            mOverlayVisible = true;
            mOverlay.setVisibility(View.VISIBLE);

<<<<<<< Updated upstream
            mFadeInAnim = AnimatorInflater.loadAnimator(this, R.animator.fade_in);
            mFadeInAnim.setTarget(mOverlay);
            mFadeInAnim.start();

            mRotateClockwiseAnim = AnimatorInflater.loadAnimator(this, R.animator.rotate_clockwise);
=======
            Animator mRotateClockwiseAnim = AnimatorInflater.loadAnimator(this, R.animator.rotate_clockwise);
>>>>>>> Stashed changes
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
