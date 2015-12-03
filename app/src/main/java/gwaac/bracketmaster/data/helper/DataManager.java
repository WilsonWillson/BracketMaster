package gwaac.bracketmaster.data.helper;

import android.text.TextUtils;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

import gwaac.bracketmaster.BracketMasterApplication;
import gwaac.bracketmaster.data.model.Match;
import gwaac.bracketmaster.data.model.Tournament;

/**
 * Created by Charlie on 11/18/15.
 */
public class DataManager {

    private static List<Tournament> mTournamentList;

    private static List<Tournament> populateData() {

        List<Tournament> tournaments = new ArrayList<>();
        List<Match> matches = new ArrayList<>();
        matches.add(new Match("charliealbright", "aryamccarthy"));
        matches.add(new Match("gavinpham", "johnwilson"));
        Tournament tournament = new Tournament()
                .setName("Rocket League Pro Finals")
                .setDescription("Watch as these 4 pro players duke it out in an impressive show of skill for a cash prize of 500 dollars!")
                .setOwner("0c18224e-7604-4147-b741-5aa6d300f311")
                .setStartDateTime(2015, 12, 4, 12, 0)
                .setEndDateTime(2015, 12, 4, 16, 0)
                .setGameImageID(GameImageLoader.ROCKET_LEAGUE)
                .setStarted(true)
                .setMatchList(matches);
        tournaments.add(tournament);

        matches = new ArrayList<>();
        matches.add(new Match("Adon", "Ryu"));
        matches.add(new Match("Geki", "Lee"));
        matches.add(new Match("Eagle", "Retsu"));
        matches.add(new Match("Sagat", "---"));

        tournament = new Tournament()
                .setName("Street Fighter RAMPAGE")
                .setDescription("Punch! Kick! Roundhouse!")
                .setOwner("")
                .setStartDateTime(2015, 12, 7, 14, 0)
                .setEndDateTime(2015, 12, 7, 22, 0)
                .setGameImageID(GameImageLoader.STREET_FIGHTER)
                .setStarted(true)
                .setMatchList(matches);
        tournaments.add(tournament);

        return tournaments;
    }

    public static List<Tournament> getTournamentData() {
        if (mTournamentList == null) {
            mTournamentList = populateData();
        }
        return mTournamentList;
    }

    public static List<Tournament> searchByTitle(String query) {
        List<Tournament> results = new ArrayList<>();

        for (int i = 0; i < getTournamentData().size(); i++) {
            String tournamentTitle = mTournamentList.get(i).getName();
            if (tournamentTitle.toLowerCase().contains(query.toLowerCase())) {
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
            if (tournamentDescription.toLowerCase().contains(query.toLowerCase())) {
                results.add(mTournamentList.get(i));
            }
        }
        return results;
    }
}
