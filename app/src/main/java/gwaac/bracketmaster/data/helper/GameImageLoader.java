package gwaac.bracketmaster.data.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

import gwaac.bracketmaster.R;

/**
 * Created by Charlie on 11/18/15.
 */
public class GameImageLoader {

    public static final int BATTLEFIELD = R.drawable.logo_battlefield;
    public static final int COUNTER_STRIKE = R.drawable.logo_counter_strike;
    public static final int DOOM = R.drawable.logo_doom;
    public static final int DOTA_2 = R.drawable.logo_dota_2;
    public static final int HALO = R.drawable.logo_halo;
    public static final int HEARTHSTONE = R.drawable.logo_hearthstone;
    public static final int HEROES_OF_THE_STORM = R.drawable.logo_heroes_of_the_storm;
    public static final int LEAGUE_OF_LEGENDS = R.drawable.logo_league_of_legends;
    public static final int ROCKET_LEAGUE = R.drawable.logo_rocket_league;
    public static final int STARCRAFT_2 = R.drawable.logo_starcraft_2;
    public static final int STREET_FIGHTER = R.drawable.logo_street_fighter;
    public static final int SUPER_SMASH_BROS = R.drawable.logo_super_smash_bros;
    public static final int UNREAL_TOURNAMENT = R.drawable.logo_unreal_tournament;
    public static final int WORLD_OF_TANKS = R.drawable.logo_world_of_tanks;

    private Map<String, Integer> mImageReourceMap;

    private Context mContext;

    public GameImageLoader(Context context) {
        mContext = context;
        mImageReourceMap = new HashMap<>();
        mImageReourceMap.put("Battlefield", BATTLEFIELD);
        mImageReourceMap.put("Counter Strike", COUNTER_STRIKE);
        mImageReourceMap.put("Doom", DOOM);
        mImageReourceMap.put("DOTA 2", DOTA_2);
        mImageReourceMap.put("Halo", HALO);
        mImageReourceMap.put("Hearthstone", HEARTHSTONE);
        mImageReourceMap.put("Heroes of the Storm", HEROES_OF_THE_STORM);
        mImageReourceMap.put("League of Legends", LEAGUE_OF_LEGENDS);
        mImageReourceMap.put("Rocket League", ROCKET_LEAGUE);
        mImageReourceMap.put("Starcraft 2", STARCRAFT_2);
        mImageReourceMap.put("Street Fighter", STREET_FIGHTER);
        mImageReourceMap.put("Super Smash Bros.", SUPER_SMASH_BROS);
        mImageReourceMap.put("Unreal Tournament", UNREAL_TOURNAMENT);
        mImageReourceMap.put("World of Tanks", WORLD_OF_TANKS);
    }

    public Bitmap getImageForID(int imageID) {
        return BitmapFactory.decodeResource(mContext.getResources(), imageID);
    }

    public Bitmap getImageForGameName(String gameName) {
        return BitmapFactory.decodeResource(mContext.getResources(), mImageReourceMap.get(gameName));
    }

    public int getIdForGameName(String gameName) {
        return mImageReourceMap.get(gameName);
    }
}
