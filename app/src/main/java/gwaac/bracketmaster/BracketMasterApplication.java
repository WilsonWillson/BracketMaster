package gwaac.bracketmaster;

import android.app.Application;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import gwaac.bracketmaster.data.helper.DataManager;
import gwaac.bracketmaster.data.model.Tournament;

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
