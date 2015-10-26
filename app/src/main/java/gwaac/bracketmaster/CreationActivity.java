package gwaac.bracketmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;

public class CreationActivity extends AppCompatActivity {

    private EditText mTournamentNameField;
    private EditText mGameNameField;
    private EditText mDescriptionField;
    private EditText mStartTimeField;
    private Button mCreateButton;

    private Notifier notifier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        notifier = new Notifier(this);

        mTournamentNameField = (EditText) findViewById(R.id.tournament_name);
        mGameNameField = (EditText) findViewById(R.id.game_name);
        mDescriptionField = (EditText) findViewById(R.id.description);
        mStartTimeField = (EditText) findViewById(R.id.startTime);
        mCreateButton = (Button) findViewById(R.id.create_tournament);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifier.setView(v);
                TournamentData td = parseTournament();
                // TODO: submit stuff.
                finish();
            }
        });
    }

    public TournamentData parseTournament() {
        TournamentData td = new TournamentData();
        td.setTournament(mTournamentNameField.getText().toString());
        td.setGame(mGameNameField.getText().toString());
        try {
            td.setStartTime(mStartTimeField.getText().toString());
        } catch (ParseException nfe) {
            notifier.alertWithConfirmation("Inadmissible date format, or date has passed.");
            return new TournamentData();
        }
        return td;
    }
}
