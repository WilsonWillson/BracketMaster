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

    public static List<Tournament> getTournamentData() {
        if (mTournamentList == null) {
            mTournamentList = new ArrayList<>();
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
