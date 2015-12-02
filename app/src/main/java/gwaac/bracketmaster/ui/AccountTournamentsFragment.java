package gwaac.bracketmaster.ui;


import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gwaac.bracketmaster.BracketMasterApplication;
import gwaac.bracketmaster.R;
import gwaac.bracketmaster.data.adapter.TournamentAdapter;
import gwaac.bracketmaster.data.helper.TournamentProperties;
import gwaac.bracketmaster.data.model.Tournament;

public class AccountTournamentsFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.my_tournaments_recycler_view)
    RecyclerView mRecyclerView;
    List<Tournament> tournaments;


    public AccountTournamentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_tournaments, container, false);
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        TournamentAdapter tournamentAdapter = new TournamentAdapter(this.getActivity(), tournaments);
        mRecyclerView.setAdapter(tournamentAdapter);


        return view;
    }

    private void initData() {
        if (tournaments == null) {
            tournaments = new ArrayList<>();
        }
        Firebase ref = ((BracketMasterApplication)getActivity().getApplication()).myFirebaseRef;
        String uid = ref.getAuth().getUid();
        Query query = ref.child("tournaments").orderByChild("ownerID").equalTo(uid);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TournamentProperties properties = dataSnapshot.getValue(TournamentProperties.class);
                Tournament tournament = TournamentProperties.toTournament(properties);

                tournaments.add(tournament);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
