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
    private List<String> mSignupList;

    private int mGameImageID;

    private boolean mStarted;

    private Calendar mStartDateTime;
    private Calendar mEndDateTime;

    private List<Match> mMatchList;

    public String getName() {
        return mName;
    }

    public Tournament setName(String name) {
        mName = name;
        return this;
    }

    public int getGameImageID() { return mGameImageID; }

    public Tournament setGameImageID(int gameImageID) {
        mGameImageID = gameImageID;
        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public Tournament setDescription(String description) {
        mDescription = description;
        return this;
    }

    public String getOwner() {
        return mOwnerId;
    }

    public Tournament setOwner(String owner) {
        mOwnerId = owner;
        return this;
    }

    public boolean isStarted() {
        return mStarted;
    }

    public Tournament setStarted(boolean started) {
        mStarted = started;
        return this;
    }

    public List<String> getSignupList() {
        return mSignupList;
    }

    public Tournament setSignupList(List<String> signupList) {
        mSignupList = signupList;
        return this;
    }

    public Tournament addSignup(String userID) {
        if (mSignupList == null) {
            mSignupList = new ArrayList<>();
        }
        mSignupList.add(userID);
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
