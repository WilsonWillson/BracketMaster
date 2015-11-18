package gwaac.bracketmaster;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charlie on 11/18/15.
 */
public class DataManager {

    private Context mContext;

    public DataManager(Context context) {
        mContext = context;
    }

    public List<Tournament> getTournamentData() {
        List<Tournament> tournamentData = new ArrayList<>();

        Tournament tournament = new Tournament();
        tournament.setName("2015 Slamfest ft. Dunkmaster Darius");
        tournament.setDescription("I don't follow the rules. I DUNK THEM.\nSingle Elimination 5v5\n32 teams - Join Now!");
        tournament.setImageID(GameImageLoader.LEAGUE_OF_LEGENDS);
        tournament.setOwner("charliealbright");
        tournament.setStartDateTime(2015, 11, 25, 14, 0);
        tournament.setEndDateTime(2015, 11, 27, 12, 30);
        tournament.addMatch(new Match("OG", "SKT"));
        tournament.addMatch(new Match("TL", "KOO"));
        tournament.addMatch(new Match("FW", "C9"));
        tournament.addMatch(new Match("TSM", "NRG"));
        tournamentData.add(tournament);

        tournament = new Tournament();
        tournament.setName("ProBuilds Annual Tournament");
        tournament.setDescription("16-Team tournament sponsored by ProBuilds, your source for all the best LoL champion builds!\nDouble Elimination 5v5");
        tournament.setImageID(GameImageLoader.LEAGUE_OF_LEGENDS);
        tournament.setOwner("gavinpham");
        tournament.setStartDateTime(2015, 12, 2, 11, 0);
        tournament.setEndDateTime(2015, 12, 3, 18, 0);
        tournament.addMatch(new Match("Team Power", "Sucky Summoners"));
        tournament.addMatch(new Match("Team BACON", "Team SAUSAGE"));
        tournamentData.add(tournament);

        tournament = new Tournament();
        tournament.setName("LoLKing Pro League Finals");
        tournament.setDescription("LoLKing, your go-to source for everything League of Legends!\nWatch the best of the best duke it out in this 32-team tournament\nSingle Elimination 5v5");
        tournament.setImageID(GameImageLoader.LEAGUE_OF_LEGENDS);
        tournament.setOwner("johnwilson");
        tournament.setStartDateTime(2015, 12, 8, 12, 30);
        tournament.setEndDateTime(2015, 12, 12, 19, 0);
        tournament.addMatch(new Match("Darius", "Garen"));
        tournament.addMatch(new Match("Riven", "Miss Fortune"));
        tournament.addMatch(new Match("Poppy", "Amumu"));
        tournament.addMatch(new Match("Gangplank", "Mordekaiser"));
        tournament.addMatch(new Match("Lucian", "Ashe"));
        tournament.addMatch(new Match("Annie", "Twisted Fate"));
        tournamentData.add(tournament);

        tournament = new Tournament();
        tournament.setName("Cosmic Aftershock vs. Team Rocket");
        tournament.setDescription("Witness the best Rocket League players duke it out in this high octane 3v3 Finals!\nBest of 7 matches.");
        tournament.setImageID(GameImageLoader.ROCKET_LEAGUE);
        tournament.setOwner("adrianhernandez");
        tournament.setStartDateTime(2015, 12, 10, 15, 0);
        tournament.setEndDateTime(2015, 12, 12, 18, 0);
        tournament.addMatch(new Match("Cosmic Aftershock", "Team Rocket"));
        tournamentData.add(tournament);

        tournament = new Tournament();
        tournament.setName("32 Team Amateur Tournament");
        tournament.setDescription("Come see where you stand against other amateur LoL players! No players above Gold rank will be accepted.\nSingle Elimination 5v5.");
        tournament.setImageID(GameImageLoader.LEAGUE_OF_LEGENDS);
        tournament.setOwner("aryamccarthy");
        tournament.setStartDateTime(2015, 12, 13, 16, 0);
        tournament.setEndDateTime(2015, 12, 15, 20, 0);
        tournament.addMatch(new Match("charliealbright", "gavinpham"));
        tournament.addMatch(new Match("adrianhernandez", "johnwilson"));
        tournament.addMatch(new Match("aryamccarthy", "------"));
        tournamentData.add(tournament);



        return tournamentData;
    }
}
