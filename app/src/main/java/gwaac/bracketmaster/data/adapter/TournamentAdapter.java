package gwaac.bracketmaster.data.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gwaac.bracketmaster.BracketMasterApplication;
import gwaac.bracketmaster.data.helper.CalendarHelper;
import gwaac.bracketmaster.data.helper.GameImageLoader;
import gwaac.bracketmaster.R;
import gwaac.bracketmaster.data.helper.TournamentProperties;
import gwaac.bracketmaster.data.model.Tournament;
import gwaac.bracketmaster.ui.BracketActivity;
import gwaac.bracketmaster.ui.MainActivity;
import gwaac.bracketmaster.ui.modal.Notifier;

/**
 * Created by Charlie on 10/27/15.
 */
public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.TournamentViewHolder> {

    private static final String TAG = TournamentAdapter.class.getSimpleName();

    private List<Tournament> mTournamentData;
    private Context mContext;
    private GameImageLoader mGameImageLoader;

    public TournamentAdapter(Context context, List<Tournament> tournamentData) {
        mContext = context;
        mGameImageLoader = new GameImageLoader(context);
        mTournamentData = tournamentData;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TournamentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_card, parent, false);
        return new TournamentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TournamentViewHolder holder, final int position) {
        holder.tournamentName.setText(mTournamentData.get(position).getName());
        holder.tournamentDetail.setText(mTournamentData.get(position).getDescription());
        holder.tournamentDateTimeStart.setText(CalendarHelper.getPrettyDateTime(mTournamentData.get(position).getStartDateTime()));
        holder.tournamentDateTimeEnd.setText(CalendarHelper.getPrettyDateTime(mTournamentData.get(position).getEndDateTime()));
        holder.tournamentImage.setImageBitmap(mGameImageLoader.getImageForID(mTournamentData.get(position).getGameImageID()));
        holder.viewTournamentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BracketActivity.class);
                intent.putExtra("tournamentData", new Gson().toJson(mTournamentData.get(position)));
                mContext.startActivity(intent);
            }
        });

        String ownerID = mTournamentData.get(position).getOwner();
        final Firebase firebase = ((BracketMasterApplication)mContext.getApplicationContext()).myFirebaseRef;
        if (firebase.getAuth() != null && TextUtils.equals(ownerID, firebase.getAuth().getUid())) {
            Log.v(TAG, "Owned: " + mTournamentData.get(position).getName());
            holder.signupTournamentButton.setText("Start");
            holder.signupTournamentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  TODO: implement start tournament functionality
                }
            });
        } else {
            holder.signupTournamentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final String uid = firebase.getAuth().getUid();
                    Query query = firebase.child("users").orderByKey().startAt(uid).endAt(uid);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String displayName = dataSnapshot.child(uid).getValue().toString();

                            if (mTournamentData.get(position).getSignupList() != null && mTournamentData.get(position).getSignupList().contains(displayName)) {
                                new AlertDialog.Builder(view.getContext())
                                        .setTitle("Tournament Signup")
                                        .setMessage("You have already signed up for this tournament.")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                            }
                                        }).show();
                            } else {
                                new AlertDialog.Builder(view.getContext())
                                        .setTitle("Tournament Signup")
                                        .setMessage("Would you like to sign up for this tournament?")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                final Tournament t = mTournamentData.get(position);
                                                t.addSignup(displayName);
                                                final Firebase ref = ((BracketMasterApplication) mContext.getApplicationContext()).myFirebaseRef;
                                                Query queryRef = ref.child("tournaments").orderByChild("name").startAt(t.getName()).endAt(t.getName());
                                                queryRef.addChildEventListener(new ChildEventListener() {
                                                    @Override
                                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                        String key = dataSnapshot.getKey();
                                                        Firebase tournaments = ref.child("tournaments/" + key);
                                                        tournaments.setValue(TournamentProperties.fromTournament(t));

                                                        Firebase signups = ref.child("signups/" + uid);
                                                        signups.child(key).setValue(true);
                                                    }

                                                    @Override
                                                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                                    }

                                                    @Override
                                                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                                                    }

                                                    @Override
                                                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                                    }

                                                    @Override
                                                    public void onCancelled(FirebaseError firebaseError) {

                                                    }
                                                });
                                                Notifier notifier = new Notifier((Activity) mContext, view);
                                                if (mContext instanceof MainActivity) {
                                                    //  If we are in the MainActivity, we need to disable the FAB Menu to prevent Animation bugginess.
                                                    notifier.setOnVisibilityChangedListener(((MainActivity) mContext).OnNotifierVisibilityChangedListener);
                                                }
                                                notifier.alertWithConfirmation("Sign up successful!");
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).show();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mTournamentData == null) {
            return 0;
        }
        return mTournamentData.size();
    }

    public class TournamentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tournament_name) TextView tournamentName;
        @Bind(R.id.tournament_description) TextView tournamentDetail;
        @Bind(R.id.tournament_datetime_start) TextView tournamentDateTimeStart;
        @Bind(R.id.tournament_datetime_end) TextView tournamentDateTimeEnd;
        @Bind(R.id.tournament_game_image) ImageView tournamentImage;
        @Bind(R.id.tournament_view_button) Button viewTournamentButton;
        @Bind(R.id.tournament_signup_button) Button signupTournamentButton;

        public TournamentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
