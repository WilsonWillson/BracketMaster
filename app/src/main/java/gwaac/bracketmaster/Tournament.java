package gwaac.bracketmaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Charlie on 10/27/15.
 */
public class Tournament {

    private Context mContext;

    private String mName;
    private String mDescription;
    private String mOwnerId;
    private List<String> mParticipantIds;

    private Calendar mStartDateTime;
    private Calendar mEndDateTime;

    private Bitmap mGameImage;

    private List<Tournament> mTournaments;

    public Tournament(Context context) {
        mContext = context;

        mName = null;
        mDescription = null;
        mOwnerId = null;
        mParticipantIds = null;

        mStartDateTime = null;
        mEndDateTime = null;

        mGameImage = null;
    }

    public List<Tournament> getSampleData() {
        mTournaments = new ArrayList<>();

        Tournament tournament = new Tournament(mContext);
        tournament.setName("2015 Slamfest ft. Dunkmaster Darius");
        tournament.setDescription("Single Elimination 5v5");
        tournament.setOwner("charliealbright");
        tournament.setGameImage(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.league_of_legends));
        tournament.setStartDateTime(2015, 11, 25, 14, 0);
        tournament.setEndDateTime(2015, 11, 27, 12, 30);
        mTournaments.add(tournament);

//        mTournaments.add(new Tournament(mContext, "ProBuilds Annual Tournament",
//                "Double Elimination 5v5\nNovember 2nd", "charliealbright",
//                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.league_of_legends)));
//        mTournaments.add(new Tournament(mContext, "LoLKing Pro League Finals",
//                "Single Match 5v5\nNovember 5th", "charliealbright",
//                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.league_of_legends)));
//        mTournaments.add(new Tournament(mContext, "Cosmic Aftershock vs. Team Rocket",
//                "Best of 7 3v3\nNovember 10th", "charliealbright",
//                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.rocket_league)));
//        mTournaments.add(new Tournament(mContext, "32 Team Amateur Tournament",
//                "Single Elimination 5v5\nNovember 12th", "charliealbright",
//                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.league_of_legends)));
        return mTournaments;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getOwner() {
        return mOwnerId;
    }

    public void setOwner(String mOwner) {
        this.mOwnerId = mOwner;
    }

    public List<String> getParticipantIds() {
        return mParticipantIds;
    }

    public void setParticipantIds(List<String> participantIds) {
        mParticipantIds = participantIds;
    }

    public void addParticipant(String participantId) {
        if (mParticipantIds == null) {
            mParticipantIds = new ArrayList<>();
        }
        mParticipantIds.add(participantId);
    }

    public Bitmap getGameImage() {
        return mGameImage;
    }

    public void setGameImage(Bitmap mGameImage) {
        this.mGameImage = mGameImage;
    }

    public Calendar getStartDateTime() {
        return mStartDateTime;
    }

    public void setStartDateTime(Calendar startDateTime) {
        mStartDateTime = startDateTime;
    }

    public void setStartDateTime(int year, int month, int day, int hour, int minute) {
        mStartDateTime = Calendar.getInstance();
        mStartDateTime.set(year, month, day, hour, minute);
    }

    public Calendar getEndDateTime() {
        return mEndDateTime;
    }

    public void setEndDateTime(Calendar endDateTime) {
        mEndDateTime = endDateTime;
    }

    public void setEndDateTime(int year, int month, int day, int hour, int minute) {
        mEndDateTime = Calendar.getInstance();
        mEndDateTime.set(year, month-1, day, hour, minute);
    }
}
