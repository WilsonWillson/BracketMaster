package gwaac.bracketmaster;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Arya on 10/22/15.
 */
public class Notifier {
    private Activity activity;
    private View view;

    public Notifier(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    public Notifier(Activity activity) {
        /* For the love of god, make sure you EVENTUALLY assign this.view to something non-null. */
        this(activity, null);
    }

    public void setView(View view) {
        this.view = view;
    }

    private void alertUser(String text, String action) {
        Snackbar.make(this.view, text, Snackbar.LENGTH_LONG).setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* pass */
            }
        }).setActionTextColor(activity.getResources().getColor(R.color.colorPrimary)).show();
    }

    public void alertEmptyField() {
        alertUser("Username or password is empty.", "OK");
    }

    public void alertUserCreated() {
        alertUser("User created!", "SWEET!");
    }

    public void alertUserNotCreated() {
        alertUser("Couldn't create account.", "OK");
    }

    public void alertFieldIncorrect() {
        alertUser("Username or password incorrect.", "OK");
    }

    public void alertSuccessfulLogin() {
        alertUser("Credentials correct!", "SWEET!");
    }

    public void alertWithConfirmation(String text) {
        alertUser(text, "OK");
    }

    @Deprecated
    public void alertMainActivity() {
        alertWithConfirmation("This is the main page of the app.");
    }
}
