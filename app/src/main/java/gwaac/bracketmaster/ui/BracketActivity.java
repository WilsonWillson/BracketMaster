package gwaac.bracketmaster.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.google.gson.Gson;

import gwaac.bracketmaster.R;
import gwaac.bracketmaster.data.model.Tournament;
import gwaac.bracketmaster.data.adapter.BracketAdapter;

/**
 * Created by Adrian on 11/14/2015.
 */

public class BracketActivity extends AppCompatActivity {

    private static final String TAG = BracketActivity.class.getSimpleName();

    private Tournament mTournament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mTournament = new Gson().fromJson(intent.getSerializableExtra("tournamentData").toString(), Tournament.class);
        Log.v(TAG, "Tournament: " + mTournament.getMatchList().size() + " matches scheduled.");

        setContentView(R.layout.activity_bracket);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.bracket_list);
        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        BracketAdapter mAdapter = new BracketAdapter(this, mTournament.getMatchList());
        mRecyclerView.setAdapter(mAdapter);
    }
}
