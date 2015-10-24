package gwaac.bracketmaster;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Arya on 10/23/15.
 */
public class BracketMasterApplication extends Application {

    /* We need this to be accessible from all Activities. */
    public Firebase myFirebaseRef;

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://scorching-inferno-5646.firebaseio.com/");
    }
}
