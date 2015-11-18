package gwaac.bracketmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by Adrian on 11/14/2015.
 */

public class BracketActivity extends AppCompatActivity {

    private static final String TAG = BracketActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bracket);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.bracket_list);
        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        BracketAdapter mAdapter = new BracketAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();
        Tournament tournament = (Tournament)intent.getSerializableExtra("tournamentData");
        Log.v(TAG, "Tournament: " + tournament.getName());
    }
}
