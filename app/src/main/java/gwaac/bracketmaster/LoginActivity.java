package gwaac.bracketmaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 137;

    @InjectView(R.id.input_email) EditText mEmailField;
    @InjectView(R.id.input_password) EditText mPasswordField;
    @InjectView(R.id.btn_login) Button mLoginButton;
    @InjectView(R.id.link_signup) TextView mRegisterButton;
    private Notifier notifier;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        notifier = new Notifier(this);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifier.setView(view);
                tryLogin();
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifier.setView(view);
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                //tryCreateAccount();
            }
        });
    }

    private void saveAccount(AuthData authData) {
        /* This may not be necessary. See returned AuthData object here:
        https://www.firebase.com/docs/android/guide/login/password.html#section-logging-in
         */
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().clear();
        preferences.edit().putString("uid", authData.getUid());
    }

    private void firebaseLogin() {
        String email = mEmailField.getText().toString();
        String pass = mPasswordField.getText().toString();

        Firebase myFirebaseRef = ((BracketMasterApplication) getApplicationContext()).myFirebaseRef;
        myFirebaseRef.authWithPassword(email, pass, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                notifier.alertSuccessfulLogin();
                saveAccount(authData);
                progressDialog.dismiss();
                mLoginButton.setEnabled(true);
                segueToMainActivity();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                notifier.alertWithConfirmation(firebaseError.getMessage());
                progressDialog.dismiss();
                mLoginButton.setEnabled(true);
            }
        });
    }

    public void segueToMainActivity() {
        this.finish();
    }

    public void tryLogin() {
        Log.d(this.getClass().toString(), "Login");

        mLoginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();


        firebaseLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here.
                String email = intent.getStringExtra("email");
                String password = intent.getStringExtra("password");
                mEmailField.setText(email);
                mPasswordField.setText(password);
                tryLogin();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to main activity.
        moveTaskToBack(true);
    }

}
