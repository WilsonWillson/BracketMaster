package gwaac.bracketmaster.data.model;

/**
 * Created by Adrian on 11/14/2015.
 */

import java.util.ArrayList;
import java.util.List;

public class Match {

    private String mPlayer1;
    private String mPlayer2;

    public Match() {
        mPlayer1 = "";
        mPlayer2 = "";
    }

    public Match(String player1, String player2) {
        mPlayer1 = player1;
        mPlayer2 = player2;
    }

    public List<Match> getSampleData() {
        List<Match> mMatches = new ArrayList<>();

        Match match = new Match();
        match.setPlayer1("Wilson");
        match.setPlayer2("John");
        mMatches.add(match);

        match = new Match();
        match.setPlayer1("Adrian");
        match.setPlayer2("Jimmy");
        mMatches.add(match);

        match = new Match();
        match.setPlayer1("Charlie");
        match.setPlayer2("Arya");
        mMatches.add(match);

        match = new Match();
        match.setPlayer1("Mr. Pinkerton");
        match.setPlayer2("Mrs. Pinkerton");
        mMatches.add(match);

        match = new Match();
        match.setPlayer1("Winnie");
        match.setPlayer2("Tigger");
        mMatches.add(match);

        match = new Match();
        match.setPlayer1("Buttmunch McGee");
        match.setPlayer2("Grim");
        mMatches.add(match);

        match = new Match();
        match.setPlayer1("Billy");
        match.setPlayer2("Mandy");
        mMatches.add(match);

        match = new Match();
        match.setPlayer1("Jack");
        match.setPlayer2("Box");
        mMatches.add(match);

        return mMatches;
    }

    public String getPlayer1() {
        return mPlayer1;
    }

    public void setPlayer1(String mPlayer1) {
        this.mPlayer1 = mPlayer1;
    }

    public String getPlayer2() {
        return mPlayer2;
    }

    public void setPlayer2(String mPlayer2) {
        this.mPlayer2 = mPlayer2;
    }
}
