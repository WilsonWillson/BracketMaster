package gwaac.bracketmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    Notifier notifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginIfNeeded();

        notifier = new Notifier(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifier.setView(view);
                notifier.alertMainActivity();
            }
        });

        Button findButton = (Button) findViewById(R.id.find_tournament_button);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifier.setView(v);
                notifier.alertWithConfirmation("Needs implementation.");
            }
        });

        Button makeButton = (Button) findViewById(R.id.make_tournament_button);
        makeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifier.setView(v);
                segueToCreation();
            }
        });
    }

    private void loginIfNeeded() {
        if (!loggedIn()) {
            segueToLogin();
        }
    }

    private boolean loggedIn() {
        Firebase ref = ((BracketMasterApplication)getApplication()).myFirebaseRef;
        return ref.getAuth() != null;
    }

    private void segueToLogin() {
        segue(LoginActivity.class);
    }

    private void segue(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    private void segueToCreation() {
        segue(CreationActivity.class);
    }

    @Override
    public void onBackPressed() {

    }

}
