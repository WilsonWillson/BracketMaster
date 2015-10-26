package gwaac.bracketmaster;

import java.text.ParseException;

/**
 * Created by Arya on 10/25/15.
 */
public class TournamentData {
    private String tournament;
    private String game;
    private String description;
    private String startTime;

    public TournamentData() {
        tournament = "";
        game = "";
        description = "";
        startTime = "";
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(String startTime) throws ParseException {
        verifyDateTimeFormat(startTime);
        this.startTime = startTime;
    }

    public void verifyDateTimeFormat(String startTime) throws ParseException {
        // TODO: needs implementation.
    }
}
