package gwaac.bracketmaster.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gwaac.bracketmaster.BracketMasterApplication;
import gwaac.bracketmaster.R;

public class AccountSettingsFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.logout_from_acct_page)
    Button logout_button;
    @Bind(R.id.user_label)
    TextView user_label;

    @OnClick(R.id.logout_from_acct_page)
    public void logout() {
        Firebase ref = ((BracketMasterApplication) getActivity().getApplication()).myFirebaseRef;
        ref.unauth();
        Intent intent = new Intent(this.getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public AccountSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);
        ButterKnife.bind(this, view);
        Firebase firebase = ((BracketMasterApplication)getActivity().getApplication()).myFirebaseRef;
        String uid = firebase.getAuth().getUid();

        Query query = firebase.child("users").orderByKey().startAt(uid).endAt(uid);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user_label.setText(dataSnapshot.getValue().toString());
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


        return view;
    }


}
