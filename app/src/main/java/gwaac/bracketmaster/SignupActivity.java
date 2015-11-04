package gwaac.bracketmaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    Notifier notifier;

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        notifier = new Notifier(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifier.setView(v);
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifier.setView(v);
                // Finish the registration screen; return to Login.
                finish();
            }
        });
    }

    protected void signup() {
        Log.d(this.getClass().toString(), "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        firebaseCreateUser();
    }

    public void firebaseCreateUser() {
        String email = _emailText.getText().toString();
        String pass = _passwordText.getText().toString();
        final String username = _nameText.getText().toString();

        final Firebase myFirebaseRef = ((BracketMasterApplication) getApplicationContext()).myFirebaseRef;
        myFirebaseRef.createUser(email, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                notifier.alertUserCreated();
                String uid = (String) stringObjectMap.get("uid");
                // Store the username for this user.
                myFirebaseRef.child("users").child(uid).setValue(username);
                onSignupSuccess();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                notifier.alertWithConfirmation(firebaseError.getMessage());
                onSignupFailed();
            }
        });

    }


    private void onSignupSuccess() {
        _signupButton.setEnabled(true);
        progressDialog.dismiss();
        Intent resultData = new Intent();
        resultData.putExtra("email", _emailText.getText().toString());
        resultData.putExtra("password", _passwordText.getText().toString());
        setResult(RESULT_OK, resultData);
        Log.d("!!!!!!!!!!!!!!!!", "GOT HERE~");
        finish();
    }

    private void onSignupFailed() {
        progressDialog.dismiss();
        _signupButton.setEnabled(true);
    }

    private boolean validate() {
        final Firebase myFirebaseRef = ((BracketMasterApplication) getApplicationContext()).myFirebaseRef;
        Firebase usersRef = myFirebaseRef.child("users");
        /* Should ensure that usernames cannot be reused. */
        return true;
    }

}
