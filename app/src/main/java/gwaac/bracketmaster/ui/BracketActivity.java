package gwaac.bracketmaster.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gwaac.bracketmaster.BracketMasterApplication;
import gwaac.bracketmaster.R;
import gwaac.bracketmaster.data.model.Match;
import gwaac.bracketmaster.data.model.Tournament;
import gwaac.bracketmaster.data.adapter.BracketAdapter;

/**
 * Created by Adrian on 11/14/2015.
 */

public class BracketActivity extends AppCompatActivity {

    private static final String TAG = BracketActivity.class.getSimpleName();

    private Tournament mTournament;
    private Match mMatch;
    private TextView mNoResultsLabel;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewTwo;

    private BracketAdapter mRoundOneAdapter;
    private BracketAdapter mRoundTwoAdapter;

    @Bind(R.id.fab_next) FloatingActionButton mNextButton;
    @Bind(R.id.fab_prev) FloatingActionButton mPrevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bracket);

        ButterKnife.bind(this);

        mNoResultsLabel = (TextView)findViewById(R.id.bracket_no_matches_label);

        Intent intent = getIntent();
        mTournament = new Gson().fromJson(intent.getSerializableExtra("tournamentData").toString(), Tournament.class);
        String owner = mTournament.getOwner();
        Firebase firebase = ((BracketMasterApplication)getApplication()).myFirebaseRef;
        String uid = firebase.getAuth().getUid();

        if (mTournament.isStarted()) {
            if (mTournament.getMatchList() == null) {
                displayNoResults();
            } else {
                if (mTournament.getMatchList().size() > 0) {
                    mRecyclerView = (RecyclerView) findViewById(R.id.bracket_list);
                    StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
                    mRoundOneAdapter = new BracketAdapter(this, mTournament.getMatchList(), TextUtils.equals(owner, uid));
                    mRecyclerView.setAdapter(mRoundOneAdapter);
                } else {
                    displayNoResults();
                }
            }
        } else {
            displayNotStarted();
        }
    }

    public void displayNoResults() {
        mNoResultsLabel.setText("No matches have been posted for this tournament. Please check back at a later time.");
        mNoResultsLabel.setVisibility(View.VISIBLE);
    }

    public void displayNotStarted() {
        mNoResultsLabel.setText("This tournament has not been started. Please check back at a later time.");
        mNoResultsLabel.setVisibility(View.VISIBLE);
    }

    public void handleMatchWinner(String username) {
        Log.v(TAG, "Name = " + username);
        if (mMatch == null) {
            mMatch = new Match();
            mMatch.setPlayer1(username);
            return;
        }
        if (mMatch.getPlayer1() != null && mMatch.getPlayer2() == null) {
            Log.v(TAG, "Setting player 2, recycling...");
            mMatch.setPlayer2(username);
            List<Match> matches = new ArrayList<>();
            matches.add(mMatch);

            mRecyclerViewTwo = (RecyclerView) findViewById(R.id.bracket_list_two);
            StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerViewTwo.setLayoutManager(mStaggeredLayoutManager);
            mRoundOneAdapter = new BracketAdapter(this, matches, true);
            mRecyclerViewTwo.setAdapter(mRoundOneAdapter);

            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerViewTwo.setVisibility(View.VISIBLE);
                }
            });
            mPrevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerViewTwo.setVisibility(View.GONE);
                }
            });
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Winner!")
                .setMessage(username + " has won the tournament!")
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();


    }
}
