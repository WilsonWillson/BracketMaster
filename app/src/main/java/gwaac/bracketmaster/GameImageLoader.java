package gwaac.bracketmaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Charlie on 11/18/15.
 */
public class GameImageLoader {

    public static final int LEAGUE_OF_LEGENDS = R.drawable.league_of_legends;
    public static final int ROCKET_LEAGUE = R.drawable.rocket_league;

    private Context mContext;

    public GameImageLoader(Context context) {
        mContext = context;
    }

    public Bitmap getImageForID(int imageID) {
        return BitmapFactory.decodeResource(mContext.getResources(), imageID);
    }
}
