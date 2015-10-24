package gwaac.bracketmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreationActivity extends AppCompatActivity {

    private EditText mTournamentNameField;
    private EditText mGameNameField;
    private EditText mDescriptionField;
    private EditText mStartTimeField;
    private Button mCreateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mTournamentNameField = (EditText) findViewById(R.id.tournament_name);
        mGameNameField = (EditText) findViewById(R.id.game_name);
        mDescriptionField = (EditText) findViewById(R.id.description);
        mStartTimeField = (EditText) findViewById(R.id.startTime);
        mCreateButton = (Button) findViewById(R.id.create_tournament);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Submit that stuff. */
            }
        });
    }
}
