package gwaac.bracketmaster.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.firebase.client.Firebase;

import org.w3c.dom.Text;

import gwaac.bracketmaster.BracketMasterApplication;
import gwaac.bracketmaster.R;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = AccountActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getUserData();
    }

    public void getUserData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String UID = preferences.getString("uid", "none");

        if (!TextUtils.equals("none", UID)) {
            Firebase myFirebaseRef = ((BracketMasterApplication) getApplicationContext()).myFirebaseRef;
            Log.v(TAG, "userData = " + myFirebaseRef.child("users").child(UID));
        }
    }

}
