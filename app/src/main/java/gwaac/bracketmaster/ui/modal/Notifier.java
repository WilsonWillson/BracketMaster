package gwaac.bracketmaster.ui.modal;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import gwaac.bracketmaster.R;

/**
 * Created by Arya on 10/22/15.
 */
public class Notifier {

    private static final String TAG = Notifier.class.getSimpleName();

    private Activity mActivity;
    private View mView;

    private OnVisibilityChangedListener mOnVisibilityChangedListener;

    public Notifier(Activity activity, View view) {
        mActivity = activity;
        mView = view;
        mOnVisibilityChangedListener = null;
    }

    public Notifier(Activity activity) {
        this(activity, null);
    }

    private void alertUser(String text, String action) {
        if (mView == null) {
            Log.e(TAG, "View reference not set, cannot create Snackbar.");
        } else {
            Snackbar.make(mView, text, Snackbar.LENGTH_LONG).setAction(action, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /* pass */
                }
            }).setActionTextColor(mActivity.getResources().getColor(R.color.colorPrimary)).setCallback(new Snackbar.Callback() {
                @Override
                public void onShown(Snackbar snackbar) {
                    super.onShown(snackbar);
                    if (mOnVisibilityChangedListener != null) {
                        mOnVisibilityChangedListener.onVisibilityChanged(true);
                    }
                }

                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    super.onDismissed(snackbar, event);
                    if (mOnVisibilityChangedListener != null) {
                        mOnVisibilityChangedListener.onVisibilityChanged(false);
                    }
                }
            }).show();
        }
    }

    public void setView(View view) {
        mView = view;
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

    public interface OnVisibilityChangedListener {
        public void onVisibilityChanged(boolean isVisible);
    }

    public void setOnVisibilityChangedListener(OnVisibilityChangedListener listener) {
        mOnVisibilityChangedListener = listener;
    }
}
