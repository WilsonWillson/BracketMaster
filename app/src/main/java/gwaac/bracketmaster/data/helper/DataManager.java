package gwaac.bracketmaster.data.helper;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import gwaac.bracketmaster.data.model.Match;
import gwaac.bracketmaster.data.model.Tournament;

/**
 * Created by Charlie on 11/18/15.
 */
public class DataManager {

    private static List<Tournament> mTournamentList = null;

    public static List<Tournament> getTournamentData() {
        if (mTournamentList == null) {
            populateTournamentData();
        }
        return mTournamentList;
    }

    public static void populateTournamentData() {
        mTournamentList = new ArrayList<>();

        Tournament tournament = new Tournament();
        tournament.setName("2015 Slamfest ft. Dunkmaster Darius");
        tournament.setDescription("I don't follow the rules. I DUNK THEM.\nSingle Elimination 5v5\n32 teams - Join Now!");
        tournament.setGameName("League of Legends");
        tournament.setOwner("charliealbright");
        tournament.setStartDateTime(2015, 11, 25, 14, 0);
        tournament.setEndDateTime(2015, 11, 27, 12, 30);
        tournament.addMatch(new Match("OG", "SKT"));
        tournament.addMatch(new Match("TL", "KOO"));
        tournament.addMatch(new Match("FW", "C9"));
        tournament.addMatch(new Match("TSM", "NRG"));
        mTournamentList.add(tournament);

        tournament = new Tournament();
        tournament.setName("ProBuilds Annual Tournament");
        tournament.setDescription("16-Team tournament sponsored by ProBuilds, your source for all the best LoL champion builds!\nDouble Elimination 5v5");
        tournament.setGameName("League of Legends");
        tournament.setOwner("gavinpham");
        tournament.setStartDateTime(2015, 12, 2, 11, 0);
        tournament.setEndDateTime(2015, 12, 3, 18, 0);
        tournament.addMatch(new Match("Team Power", "Sucky Summoners"));
        tournament.addMatch(new Match("Team BACON", "Team SAUSAGE"));
        mTournamentList.add(tournament);

        tournament = new Tournament();
        tournament.setName("LoLKing Pro League Finals");
        tournament.setDescription("LoLKing, your go-to source for everything League of Legends!\nWatch the best of the best duke it out in this 32-team tournament\nSingle Elimination 5v5");
        tournament.setGameName("League of Legends");
        tournament.setOwner("johnwilson");
        tournament.setStartDateTime(2015, 12, 8, 12, 30);
        tournament.setEndDateTime(2015, 12, 12, 19, 0);
        tournament.addMatch(new Match("Darius", "Garen"));
        tournament.addMatch(new Match("Riven", "Miss Fortune"));
        tournament.addMatch(new Match("Poppy", "Amumu"));
        tournament.addMatch(new Match("Gangplank", "Mordekaiser"));
        tournament.addMatch(new Match("Lucian", "Ashe"));
        tournament.addMatch(new Match("Annie", "Twisted Fate"));
        mTournamentList.add(tournament);

        tournament = new Tournament();
        tournament.setName("Cosmic Aftershock vs. Team Rocket");
        tournament.setDescription("Witness the best Rocket League players duke it out in this high octane 3v3 Finals!\nBest of 7 matches.");
        tournament.setGameName("Rocket League");
        tournament.setOwner("adrianhernandez");
        tournament.setStartDateTime(2015, 12, 10, 15, 0);
        tournament.setEndDateTime(2015, 12, 12, 18, 0);
        tournament.addMatch(new Match("Cosmic Aftershock", "Team Rocket"));
        mTournamentList.add(tournament);

        tournament = new Tournament();
        tournament.setName("32 Team Amateur Tournament");
        tournament.setDescription("Come see where you stand against other amateur LoL players! No players above Gold rank will be accepted.\nSingle Elimination 5v5.");
        tournament.setGameName("League of Legends");
        tournament.setOwner("aryamccarthy");
        tournament.setStartDateTime(2015, 12, 13, 16, 0);
        tournament.setEndDateTime(2015, 12, 15, 20, 0);
        tournament.addMatch(new Match("charliealbright", "gavinpham"));
        tournament.addMatch(new Match("adrianhernandez", "johnwilson"));
        tournament.addMatch(new Match("aryamccarthy", "------"));
        mTournamentList.add(tournament);
    }

    public static List<Tournament> searchByTitle(String query) {
        List<Tournament> results = new ArrayList<>();

        for (int i = 0; i < getTournamentData().size(); i++) {
            String tournamentTitle = mTournamentList.get(i).getName();
            if (tournamentTitle.contains(query)) {
                results.add(mTournamentList.get(i));
            }
        }
        return results;
    }

    public static List<Tournament> searchByOwner(String query) {
        List<Tournament> results = new ArrayList<>();

        for (int i = 0; i < getTournamentData().size(); i++) {
            String tournamentOwner = mTournamentList.get(i).getOwner();
            if (TextUtils.equals(tournamentOwner.toLowerCase(), query.toLowerCase())) {
                results.add(mTournamentList.get(i));
            }
        }
        return results;
    }

    public static List<Tournament> searchByDescription(String query) {
        List<Tournament> results = new ArrayList<>();

        for (int i = 0; i < getTournamentData().size(); i++) {
            String tournamentDescription = mTournamentList.get(i).getDescription();
            if (tournamentDescription.contains(query)) {
                results.add(mTournamentList.get(i));
            }
        }
        return results;
    }
}
