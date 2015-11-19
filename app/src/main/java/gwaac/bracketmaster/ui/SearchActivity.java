package gwaac.bracketmaster.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.v7.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gwaac.bracketmaster.R;

import butterknife.ButterKnife;
import butterknife.Bind;
import gwaac.bracketmaster.data.adapter.TournamentAdapter;
import gwaac.bracketmaster.data.helper.DataManager;
import gwaac.bracketmaster.data.model.Tournament;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    @Bind(R.id.search_result_recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.search_result_no_results_label) TextView mNoResultsLabel;

    List<Tournament> tournaments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tournaments = DataManager.getTournamentData();
        TournamentAdapter mAdapter = new TournamentAdapter(this, tournaments);
        mRecyclerView.setAdapter(mAdapter);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        handleIntent(intent);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<Tournament> mTournamentList = getSearchResults(query);
            if (mTournamentList == null || mTournamentList.size() == 0) {
                displayNoResults();
            } else {
                mNoResultsLabel.setVisibility(View.GONE);
                tournaments = mTournamentList;
                TournamentAdapter newAdapter = new TournamentAdapter(this, tournaments);
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return true;
    }
}
