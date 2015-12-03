package gwaac.bracketmaster.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

    public static final String TAG = AccountSettingsFragment.class.getSimpleName();

    @Bind(R.id.logout_from_acct_page) Button logout_button;
    @Bind(R.id.user_label) TextView user_label;

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

    @OnClick(R.id.logout_from_acct_page)
    public void logout() {
        Firebase ref = ((BracketMasterApplication) getActivity().getApplication()).myFirebaseRef;
        ref.unauth();
        Intent intent = new Intent(this.getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.change_email_btn)
    public void changeEmail() {
        Log.v(TAG, "FUCK");
        LayoutInflater inflater = getLayoutInflater(null);
        View dialogView = inflater.inflate(R.layout.dialog_change_email, null);
        final TextInputLayout emailField = (TextInputLayout)dialogView.findViewById(R.id.change_email_field);
        final TextInputLayout passwordField = (TextInputLayout)dialogView.findViewById(R.id.change_email_password);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext())
                .setTitle("Change Email")
                .setView(dialogView)
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (emailField.getEditText() != null && passwordField.getEditText() != null) {
                            String newEmail = emailField.getEditText().getText().toString();
                            String newPassword = passwordField.getEditText().getText().toString();
                            Log.v(TAG, "New Password = " + newPassword);
                            Log.v(TAG, "New Email = " + newEmail);
                            // TODO: Save new email to Firebase (ARYA)
                            dialogInterface.dismiss();
                        }

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

}
