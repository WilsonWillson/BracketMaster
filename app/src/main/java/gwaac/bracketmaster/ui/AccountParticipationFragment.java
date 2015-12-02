package gwaac.bracketmaster.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;
import gwaac.bracketmaster.BracketMasterApplication;
import gwaac.bracketmaster.R;
import gwaac.bracketmaster.data.adapter.TournamentAdapter;
import gwaac.bracketmaster.data.helper.TournamentProperties;
import gwaac.bracketmaster.data.model.Tournament;

public class AccountParticipationFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.participation_recycler_view)
    RecyclerView mRecyclerView;
    List<Tournament> tournaments;

    public AccountParticipationFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_participation, container, false);
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
        final Firebase ref = ((BracketMasterApplication)getActivity().getApplication()).myFirebaseRef;
        String uid = ref.getAuth().getUid();
        Firebase participation = ref.child("signups/" + uid);
        participation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map signups = dataSnapshot.getValue(Map.class);

                if (signups == null) return; // Get out if it doesn't work.
                for (Object o : signups.keySet()) {
                    String tournamentID = (String) o;

                    final Firebase tournaments = ref.child("tournaments/" + tournamentID);
                    tournaments.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            TournamentProperties tp = dataSnapshot.getValue(TournamentProperties.class);
                            Tournament t = TournamentProperties.toTournament(tp);
                            AccountParticipationFragment.this.tournaments.add(t);
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}
