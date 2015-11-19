package gwaac.bracketmaster.data.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Charlie on 10/27/15.
 */
public class Tournament {

    private String mName;
    private String mDescription;
    private String mOwnerId;

    private int mImageID;

    private Calendar mStartDateTime;
    private Calendar mEndDateTime;

    private List<Match> mMatchList;

    private static List<Tournament> mSampleData;

    public static List<Tournament> getSampleData() {
        if (mSampleData == null) {
            mSampleData = new ArrayList<>();

            Tournament tournament = new Tournament();
            tournament.setName("2015 Slamfest ft. Dunkmaster Darius");
            tournament.setDescription("I don't follow the rules. I DUNK THEM.\nSingle Elimination 5v5\n32 teams - Join Now!");
            tournament.setOwner("charliealbright");
            tournament.setStartDateTime(2015, 11, 25, 14, 0);
            tournament.setEndDateTime(2015, 11, 27, 12, 30);
            mSampleData.add(tournament);

            tournament = new Tournament();
            tournament.setName("ProBuilds Annual Tournament");
            tournament.setDescription("16-Team tournament sponsored by ProBuilds, your source for all the best LoL champion builds!\nDouble Elimination 5v5");
            tournament.setOwner("gavinpham");
            tournament.setStartDateTime(2015, 12, 2, 11, 0);
            tournament.setEndDateTime(2015, 12, 3, 18, 0);
            mSampleData.add(tournament);

            tournament = new Tournament();
            tournament.setName("LoLKing Pro League Finals");
            tournament.setDescription("LoLKing, your go-to source for everything League of Legends!\nWatch the best of the best duke it out in this 32-team tournament\nSingle Elimination 5v5");
            tournament.setOwner("johnwilson");
            tournament.setStartDateTime(2015, 12, 8, 12, 30);
            tournament.setEndDateTime(2015, 12, 12, 19, 0);
            mSampleData.add(tournament);

            tournament = new Tournament();
            tournament.setName("Cosmic Aftershock vs. Team Rocket");
            tournament.setDescription("Witness the best Rocket League players duke it out in this high octane 3v3 Finals!\nBest of 7 matches.");
            tournament.setOwner("adrianhernandez");
            tournament.setStartDateTime(2015, 12, 10, 15, 0);
            tournament.setEndDateTime(2015, 12, 12, 18, 0);
            mSampleData.add(tournament);

            tournament = new Tournament();
            tournament.setName("32 Team Amateur Tournament");
            tournament.setDescription("Come see where you stand against other amateur LoL players! No players above Gold rank will be accepted.\nSingle Elimination 5v5.");
            tournament.setOwner("aryamccarthy");
            tournament.setStartDateTime(2015, 12, 13, 16, 0);
            tournament.setEndDateTime(2015, 12, 15, 20, 0);
            mSampleData.add(tournament);

        }
        return mSampleData;
    }

    public String getName() {
        return mName;
    }

    public Tournament setName(String mName) {
        this.mName = mName;
        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public Tournament setDescription(String mDescription) {
        this.mDescription = mDescription;
        return this;
    }

    public String getOwner() {
        return mOwnerId;
    }

    public Tournament setOwner(String mOwner) {
        this.mOwnerId = mOwner;
        return this;
    }

    public List<Match> getMatchList() {
        return mMatchList;
    }

    public Tournament setMatchList(List<Match> matchList) {
        mMatchList = matchList;
        return this;
    }

    public void addMatch(Match match) {
        if (mMatchList == null) {
            mMatchList = new ArrayList<>();
        }
        mMatchList.add(match);
    }

    public int getImageID() {
        return mImageID;
    }

    public Tournament setImageID(int imageID) {
        this.mImageID = imageID;
        return this;
    }

    public Calendar getStartDateTime() {
        return mStartDateTime;
    }

    public Tournament setStartDateTime(Calendar startDateTime) {
        mStartDateTime = startDateTime;
        return this;
    }

    public Tournament setStartDateTime(int year, int month, int day, int hour, int minute) {
        mStartDateTime = Calendar.getInstance();
        mStartDateTime.set(year, month-1, day, hour, minute);
        return this;
    }

    public Calendar getEndDateTime() {
        return mEndDateTime;
    }

    public Tournament setEndDateTime(Calendar endDateTime) {
        mEndDateTime = endDateTime;
        return this;
    }

    public Tournament setEndDateTime(int year, int month, int day, int hour, int minute) {
        mEndDateTime = Calendar.getInstance();
        mEndDateTime.set(year, month-1, day, hour, minute);
        return this;
    }
}
