package gwaac.bracketmaster;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FloatingActionButton mFab;
    private FloatingActionButton mFab2;
    private Notifier mNotifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginIfNeeded();

        mRecyclerView = (RecyclerView)findViewById(R.id.tournament_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TournamentAdapter(new Tournament(this).getSampleData());
        mRecyclerView.setAdapter(mAdapter);

        mNotifier = new Notifier(this);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segueToCreation();
            }
        });

        mFab2 = (FloatingActionButton)findViewById(R.id.fab2);
        mFab2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        mFab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segueToSearch();
            }
        });
    }

    private void loginIfNeeded() {
        if (!loggedIn()) {
            segueToLogin();
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
    @Override
    public void onBackPressed() {

    }

}
