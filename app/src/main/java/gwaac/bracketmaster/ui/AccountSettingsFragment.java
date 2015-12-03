package gwaac.bracketmaster.ui;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.AuthData;
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
import gwaac.bracketmaster.data.helper.TournamentProperties;
import gwaac.bracketmaster.ui.modal.Notifier;

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
        LayoutInflater inflater = getLayoutInflater(null);
        View dialogView = inflater.inflate(R.layout.dialog_change_email, null);
        final TextInputLayout emailField = (TextInputLayout)dialogView.findViewById(R.id.change_email_field);
        final TextInputLayout passwordField = (TextInputLayout)dialogView.findViewById(R.id.change_email_password);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext())
                .setTitle("Change Email")
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (emailField.getEditText() != null && passwordField.getEditText() != null) {
                            final String newEmail = emailField.getEditText().getText().toString();
                            String password = passwordField.getEditText().getText().toString();
                            Log.v(TAG, "Password = " + password);
                            Log.v(TAG, "New Email = " + newEmail);

                            /*final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                                    R.style.AppTheme_Dark_Dialog);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Logging in...");
                            progressDialog.show();*/
                            final DialogInterface di = dialogInterface;

                            Firebase myFirebaseRef = ((BracketMasterApplication) getActivity().getApplicationContext()).myFirebaseRef;

                            String oldEmail = (String) myFirebaseRef.getAuth().getProviderData().get("email");
                            System.out.println(oldEmail);
                            myFirebaseRef.changeEmail(
                                    oldEmail,
                                    password,
                                    newEmail,
                                    new Firebase.ResultHandler() {
                                        @Override
                                        public void onSuccess() {
                                            Notifier notifier = new Notifier(getActivity(), getView());
                                            notifier.alertWithConfirmation("Email changed to " + newEmail);
                                            /*progressDialog.dismiss();*/
                                            di.dismiss();
                                        }

                                        @Override
                                        public void onError(FirebaseError firebaseError) {
                                            Notifier notifier = new Notifier(getActivity(), getView());
                                            notifier.alertWithConfirmation(firebaseError.getMessage());
                                            /*progressDialog.dismiss();*/
                                        }
                                    }
                            );


                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    @OnClick(R.id.change_password_btn)
    public void changePassword() {
        LayoutInflater inflater = getLayoutInflater(null);
        View dialogView = inflater.inflate(R.layout.dialog_change_password, null);
        final TextInputLayout currentPasswordField = (TextInputLayout)dialogView.findViewById(R.id.change_password_current);
        final TextInputLayout newPasswordField = (TextInputLayout)dialogView.findViewById(R.id.change_password_new);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext())
                .setTitle("Change Password")
                .setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (currentPasswordField.getEditText() != null && newPasswordField.getEditText() != null) {
                            String currentPassword = currentPasswordField.getEditText().getText().toString();
                            String newPassword = newPasswordField.getEditText().getText().toString();
                            Log.v(TAG, "Current Password = " + currentPassword);
                            Log.v(TAG, "New Password = " + newPassword);

                            Firebase myFirebaseRef = ((BracketMasterApplication) getActivity().getApplicationContext()).myFirebaseRef;
                            String oldEmail = (String) myFirebaseRef.getAuth().getProviderData().get("email");
                            System.out.println(oldEmail);
                            myFirebaseRef.changePassword(oldEmail,
                                    currentPassword,
                                    newPassword,
                                    new Firebase.ResultHandler() {
                                        @Override
                                        public void onSuccess() {
                                            Notifier notifier = new Notifier(getActivity(), getView());
                                            notifier.alertWithConfirmation("Success! Password changed.");
                                        }

                                        @Override
                                        public void onError(FirebaseError firebaseError) {
                                            Notifier notifier = new Notifier(getActivity(), getView());
                                            notifier.alertWithConfirmation(firebaseError.getMessage());
                                        }
                                    });

                            dialogInterface.dismiss();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

}
