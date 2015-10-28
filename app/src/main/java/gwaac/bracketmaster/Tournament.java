package gwaac.bracketmaster;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charlie on 10/27/15.
 */
public class Tournament {
    private Context mContext;
    private String mName;
    private String mDescription;
    private String mOwner;
    private Bitmap mGameImage;

    private List<Tournament> mTournaments;

    public Tournament(Context context) {
        mContext = context;
    }

    public Tournament(Context context, String name, String description, String owner, Bitmap gameImage) {
        mContext = context;
        mName = name;
        mDescription = description;
        mOwner = "Owner: " + owner;
        mGameImage = gameImage;
    }

    public List<Tournament> getSampleData() {
        mTournaments = new ArrayList<>();
        mTournaments.add(new Tournament(mContext, "2015 Slamfest ft. Dunkmaster Darius",
                "Single Elimination 5v5\nOctober 28th", "charliealbright",
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.league_of_legends)));
        mTournaments.add(new Tournament(mContext, "ProBuilds Annual Tournament",
                "Double Elimination 5v5\nNovember 2nd", "charliealbright",
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.league_of_legends)));
        mTournaments.add(new Tournament(mContext, "LoLKing Pro League Finals",
                "Single Match 5v5\nNovember 5th", "charliealbright",
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.league_of_legends)));
        mTournaments.add(new Tournament(mContext, "Cosmic Aftershock vs. Team Rocket",
                "Best of 7 3v3\nNovember 10th", "charliealbright",
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.rocket_league)));
        mTournaments.add(new Tournament(mContext, "32 Team Amateur Tournament",
                "Single Elimination 5v5\nNovember 12th", "charliealbright",
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.league_of_legends)));
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
        return mOwner;
    }

    public void setOwner(String mOwner) {
        this.mOwner = mOwner;
    }

    public Bitmap getGameImage() {
        return mGameImage;
    }

    public void setGameImage(Bitmap mGameImage) {
        this.mGameImage = mGameImage;
    }
}
