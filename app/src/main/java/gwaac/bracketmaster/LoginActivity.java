package gwaac.bracketmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    final static String PREFS_NAME = "BracketMasterPrefs";

    private String mUserEmail;
    private String mUserPassword;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginButton;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkFirstTimeSetup();

        Firebase.setAndroidContext(this);
        final Firebase myFirebaseRef = new Firebase("https://scorching-inferno-5646.firebaseio.com/");

        mEmailField = (EditText)findViewById(R.id.email_field);
        mPasswordField = (EditText)findViewById(R.id.password_field);
        mLoginButton = (Button)findViewById(R.id.login);
        mRegisterButton = (Button)findViewById(R.id.register);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryLogin(view);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (someFieldIsEmpty()) {
                    Snackbar.make(view, "Username or Password is empty.", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
                else {
                    firebaseCreateUser(view);
                }

            }
        });
    }

    private boolean someFieldIsEmpty() {
        return TextUtils.isEmpty(mEmailField.getText()) || TextUtils.isEmpty(mPasswordField.getText());
    }

    public void firebaseCreateUser(final View view) {
        final Firebase myFirebaseRef = new Firebase("https://scorching-inferno-5646.firebaseio.com/");
        String email = mEmailField.getText().toString();
        String pass = mPasswordField.getText().toString();

        myFirebaseRef.createUser(email, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                Snackbar.make(view, "User created!", Snackbar.LENGTH_LONG).setAction("SWEET!", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();

                tryLogin(view);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Snackbar.make(view, "Could not create user.", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
            }
        });
    }

    public void checkFirstTimeSetup() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        if (sharedPreferences.getBoolean("first_launch", true)) {
            // First launch, save some default prefs
            Log.v("[checkFirstTimeSetup]", "First Launch!");
            sharedPreferences.edit().putBoolean("first_launch", false).apply();
            sharedPreferences.edit().putString("user_email", "admin@bracketmaster.com").apply();
            sharedPreferences.edit().putString("user_password", "fuckdapolice").apply();
        } else {
            mUserEmail = sharedPreferences.getString("user_email", "err");
            mUserPassword = sharedPreferences.getString("user_password", "err");
        }
    }

    public void tryLogin(View view) {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Snackbar.make(view, "Username or Password is empty.", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
        } else {
            if (TextUtils.equals(email, mUserEmail) && TextUtils.equals(password, mUserPassword)) {
                // Correct Login
                Snackbar.make(view, "Credentials correct!", Snackbar.LENGTH_LONG).setAction("SWEET!", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                // Incorrect Login
                Snackbar.make(view, "Username or Password incorrect.", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
            }
        }
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
