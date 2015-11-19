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

    public List<Tournament> getSampleData() {
        List<Tournament> mTournaments = new ArrayList<>();

        Tournament tournament = new Tournament();
        tournament.setName("2015 Slamfest ft. Dunkmaster Darius");
        tournament.setDescription("I don't follow the rules. I DUNK THEM.\nSingle Elimination 5v5\n32 teams - Join Now!");
        tournament.setOwner("charliealbright");
        tournament.setStartDateTime(2015, 11, 25, 14, 0);
        tournament.setEndDateTime(2015, 11, 27, 12, 30);
        mTournaments.add(tournament);

        tournament = new Tournament();
        tournament.setName("ProBuilds Annual Tournament");
        tournament.setDescription("16-Team tournament sponsored by ProBuilds, your source for all the best LoL champion builds!\nDouble Elimination 5v5");
        tournament.setOwner("gavinpham");
        tournament.setStartDateTime(2015, 12, 2, 11, 0);
        tournament.setEndDateTime(2015, 12, 3, 18, 0);
        mTournaments.add(tournament);

        tournament = new Tournament();
        tournament.setName("LoLKing Pro League Finals");
        tournament.setDescription("LoLKing, your go-to source for everything League of Legends!\nWatch the best of the best duke it out in this 32-team tournament\nSingle Elimination 5v5");
        tournament.setOwner("johnwilson");
        tournament.setStartDateTime(2015, 12, 8, 12, 30);
        tournament.setEndDateTime(2015, 12, 12, 19, 0);
        mTournaments.add(tournament);

        tournament = new Tournament();
        tournament.setName("Cosmic Aftershock vs. Team Rocket");
        tournament.setDescription("Witness the best Rocket League players duke it out in this high octane 3v3 Finals!\nBest of 7 matches.");
        tournament.setOwner("adrianhernandez");
        tournament.setStartDateTime(2015, 12, 10, 15, 0);
        tournament.setEndDateTime(2015, 12, 12, 18, 0);
        mTournaments.add(tournament);

        tournament = new Tournament();
        tournament.setName("32 Team Amateur Tournament");
        tournament.setDescription("Come see where you stand against other amateur LoL players! No players above Gold rank will be accepted.\nSingle Elimination 5v5.");
        tournament.setOwner("aryamccarthy");
        tournament.setStartDateTime(2015, 12, 13, 16, 0);
        tournament.setEndDateTime(2015, 12, 15, 20, 0);
        mTournaments.add(tournament);

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

    public List<Match> getMatchList() {
        return mMatchList;
    }

    public void setMatchList(List<Match> matchList) {
        mMatchList = matchList;
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

    public void setImageID(int imageID) {
        this.mImageID = imageID;
    }

    public Calendar getStartDateTime() {
        return mStartDateTime;
    }

    public void setStartDateTime(Calendar startDateTime) {
        mStartDateTime = startDateTime;
    }

    public void setStartDateTime(int year, int month, int day, int hour, int minute) {
        mStartDateTime = Calendar.getInstance();
        mStartDateTime.set(year, month-1, day, hour, minute);
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