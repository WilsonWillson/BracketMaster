package gwaac.bracketmaster.data.helper;

import java.util.Calendar;
import java.util.List;

import gwaac.bracketmaster.data.model.Tournament;

/**
 * Created by Arya on 11/18/15.
 */
public class TournamentProperties {
    public String name;
    public String description;
    public String ownerID;
    public String gameName;
    public List<String> participantIDs;
    public Long startTime;
    public Long endTime;

    public static TournamentProperties fromTournament(Tournament tournament) {
        TournamentProperties tp = new TournamentProperties();
        tp.name = tournament.getName();
        tp.description = tournament.getDescription();
        tp.ownerID = tournament.getOwner();
        tp.gameName = tournament.getGameName();
        tp.participantIDs = null;
        tp.startTime = tournament.getStartDateTime().getTime().getTime();
        tp.endTime = tournament.getEndDateTime().getTime().getTime();
        return tp;
    }

    public static Tournament toTournament(TournamentProperties tp) {
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(tp.startTime);

        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(tp.endTime);

        Tournament t = new Tournament()
                .setName(tp.name)
                .setDescription(tp.description)
                .setOwner(tp.ownerID)
                .setGameName(tp.gameName)
                .setStartDateTime(start)
                .setEndDateTime(end);

        return t;
    }
}
