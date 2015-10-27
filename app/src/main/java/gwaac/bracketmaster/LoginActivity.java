package gwaac.bracketmaster;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginButton;
    private Button mRegisterButton;
    private Notifier notifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        notifier = new Notifier(this);

        mEmailField = (EditText) findViewById(R.id.email_field);
        mPasswordField = (EditText) findViewById(R.id.password_field);
        mLoginButton = (Button) findViewById(R.id.login);
        mRegisterButton = (Button) findViewById(R.id.register);

        mEmailField.requestFocus();

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
                tryCreateAccount();
            }
        });
    }

    private void tryCreateAccount() {
        firebaseCreateUser();
    }

    public void firebaseCreateUser() {
        String email = mEmailField.getText().toString();
        String pass = mPasswordField.getText().toString();

        Firebase myFirebaseRef = ((BracketMasterApplication) getApplicationContext()).myFirebaseRef;
        myFirebaseRef.createUser(email, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                notifier.alertUserCreated();
                tryLogin();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                notifier.alertWithConfirmation(firebaseError.getMessage());
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
                segueToMainActivity();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                notifier.alertWithConfirmation(firebaseError.getMessage());
            }
        });
    }

    public void segueToMainActivity() {
        this.finish();
    }

    public void tryLogin() {
        firebaseLogin();
    }

    @Override
    public void onBackPressed() {
        // Disable going back to main activity.
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
