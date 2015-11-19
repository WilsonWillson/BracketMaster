package gwaac.bracketmaster.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gwaac.bracketmaster.R;
import gwaac.bracketmaster.data.adapter.TournamentAdapter;
import gwaac.bracketmaster.data.helper.DataManager;
import gwaac.bracketmaster.data.model.Tournament;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity {

    private static final String TAG = SearchResultActivity.class.getSimpleName();

    @Bind(R.id.search_result_recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.search_result_no_results_label) TextView mNoResultsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String searchString = intent.getStringExtra("searchString");

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getSearchResults(query);
        }


        List<Tournament> mTournamentList = getSearchResults(searchString);
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

    public List<Tournament> getSearchResults(String query) {
        Set<Tournament> tournaments = new HashSet<>();
        tournaments.addAll(DataManager.searchByTitle(query));
        tournaments.addAll(DataManager.searchByOwner(query));
        tournaments.addAll(DataManager.searchByDescription(query));
        List<Tournament> result = new ArrayList<>();
        result.addAll(tournaments);
        return result;
    }

    public void displayNoResults() {
        mNoResultsLabel.setText("No Results found.\nPlease return to the search page and try again.");
        mNoResultsLabel.setVisibility(View.VISIBLE);
    }

}
