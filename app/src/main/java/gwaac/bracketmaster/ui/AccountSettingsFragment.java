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
import com.firebase.client.ValueEventListener;

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
        final String uid = firebase.getAuth().getUid();

        Query query = firebase.child("users").orderByKey().startAt(uid).endAt(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user_label.setText(dataSnapshot.child(uid).getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return view;
    }


}
