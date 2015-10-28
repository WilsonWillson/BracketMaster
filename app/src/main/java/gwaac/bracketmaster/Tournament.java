package gwaac.bracketmaster;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by Charlie on 10/27/15.
 */
public class Tournament {
    private String mName;
    private String mDescription;
    private String mOwner;
    private Drawable mGameImage;

    private List<Tournament> mTournaments;

    Tournament(String name, String description, String owner, Drawable gameImage) {
        mName = name;
        mDescription = description;
        mOwner = owner;
        mGameImage = gameImage;
    }

    public List<Tournament> getSampleData() {

    }
}
