package gwaac.bracketmaster.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import gwaac.bracketmaster.R;
import gwaac.bracketmaster.data.adapter.TournamentAdapter;
import gwaac.bracketmaster.data.helper.DataManager;
import gwaac.bracketmaster.data.model.Tournament;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity {

    private static final String TAG = SearchResultActivity.class.getSimpleName();

    public static final int SEARCH_TYPE_TITLE = 1;
    public static final int SEARCH_TYPE_OWNER = 2;

    private DataManager mDataManager;
    private List<Tournament> mTournamentList;

    @Bind(R.id.search_result_recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.search_result_no_results_label) TextView mNoResultsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        ButterKnife.bind(this);

        mDataManager = new DataManager(this);

        Intent intent = getIntent();
        int searchType = intent.getIntExtra("searchType", 0);
        String searchString = intent.getStringExtra("searchString");

        mTournamentList = getSearchResults(searchType, searchString);
        if (mTournamentList == null || mTournamentList.size() == 0) {
            displayNoResults();
        } else {
            mRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            TournamentAdapter mAdapter = new TournamentAdapter(this, mTournamentList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public List<Tournament> getSearchResults(int searchType, String query) {
        if (searchType == SEARCH_TYPE_TITLE) {
            return mDataManager.searchByTitle(query);
        } else if (searchType == SEARCH_TYPE_OWNER) {
            return mDataManager.searchByOwner(query);
        } else {
            return null;
        }
    }

    public void displayNoResults() {
        mNoResultsLabel.setText("No Results found.\nPlease return to the search page and try again.");
        mNoResultsLabel.setVisibility(View.VISIBLE);
    }

}
