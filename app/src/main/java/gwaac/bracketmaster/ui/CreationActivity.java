package gwaac.bracketmaster.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.firebase.client.Firebase;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import gwaac.bracketmaster.BracketMasterApplication;
import gwaac.bracketmaster.data.helper.GameImageLoader;
import gwaac.bracketmaster.data.helper.TournamentProperties;
import gwaac.bracketmaster.data.helper.CalendarHelper;
import gwaac.bracketmaster.data.model.Tournament;
import gwaac.bracketmaster.ui.modal.DatePickerFragment;
import gwaac.bracketmaster.R;
import gwaac.bracketmaster.ui.modal.TimePickerFragment;

public class CreationActivity extends AppCompatActivity implements DatePickerFragment.OnDateChosenListener, TimePickerFragment.OnTimeChosenListener {

    @Bind(R.id.tournament_name) EditText mTournamentNameField;
    @Bind(R.id.tournament_game_spinner) Spinner mGameSpinner;
    @Bind(R.id.description) EditText mDescriptionField;

    @Bind(R.id.tournament_date_start) TextInputLayout mDatePickerStartButton;
    @Bind(R.id.tournament_date_end) TextInputLayout mDatePickerEndButton;
    @Bind(R.id.tournament_time_start) TextInputLayout mTimePickerStartButton;
    @Bind(R.id.tournament_time_end) TextInputLayout mTimePickerEndButton;

    @Bind(R.id.create_tournament) Button mCreateButton;

    private GameImageLoader mGameImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);

        mGameImageLoader = new GameImageLoader(this);

        if (mDatePickerStartButton.getEditText() != null) {
            mDatePickerStartButton.getEditText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment dateFragment = DatePickerFragment.newInstance(DatePickerFragment.CREATION_START_DATE);
                    dateFragment.show(getSupportFragmentManager(), "startDate");
                }
            });
        }

        if (mDatePickerEndButton.getEditText() != null) {
            mDatePickerEndButton.getEditText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment dateFragment = DatePickerFragment.newInstance(DatePickerFragment.CREATION_END_DATE);
                    dateFragment.show(getSupportFragmentManager(), "endDate");
                }
            });
        }

        if (mTimePickerStartButton.getEditText() != null) {
            mTimePickerStartButton.getEditText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment timeFragment = TimePickerFragment.newInstance(TimePickerFragment.CREATION_START_TIME);
                    timeFragment.show(getSupportFragmentManager(), "startTime");
                }
            });
        }

        if (mTimePickerEndButton.getEditText() != null) {
            mTimePickerEndButton.getEditText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment timeFragment = TimePickerFragment.newInstance(TimePickerFragment.CREATION_END_TIME);
                    timeFragment.show(getSupportFragmentManager(), "endTime");
                }
            });
        }

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tournamentName = mTournamentNameField.getText().toString();
                String gameName = mGameSpinner.getSelectedItem().toString();
                String description = mDescriptionField.getText().toString();
                String owner = PreferenceManager.getDefaultSharedPreferences(CreationActivity.this).getString("uid", "null");
                if (owner.equals("null")) Log.i("[submitting tourney]", "Nobody's logged in.");
                Tournament tournament = new Tournament()
                        .setOwner(owner)
                        .setDescription(description)
                        .setName(tournamentName)
                        .setGameImageID(mGameImageLoader.getIdForGameName(gameName))
                        .setStartDateTime(start)
                        .setEndDateTime(end);
                Log.i("[submitting tourney]", "Oh no. What's going wrong?");
                CreationActivity.this.submitToFirebase(tournament);
                // TODO: We never actually use gameName.
                // TODO: submit stuff.
                finish();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.game_titles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGameSpinner.setAdapter(adapter);
    }

    private void submitToFirebase(Tournament t) {
        TournamentProperties tp = TournamentProperties.fromTournament(t);
        Firebase myFirebaseRef = ((BracketMasterApplication) getApplicationContext()).myFirebaseRef;
        myFirebaseRef.child("tournaments").push().setValue(tp);

    }

    private Calendar start = Calendar.getInstance(), end = Calendar.getInstance();

    @Override
    public void onDateSet(DatePicker view, int flag, int year, int month, int day) {
        String dateText = CalendarHelper.getPrettyDate(year, month, day);
        switch (flag) {
            case DatePickerFragment.CREATION_START_DATE:
                mDatePickerStartButton.getEditText().setText(dateText);
                start.set(Calendar.YEAR, year);
                start.set(Calendar.MONTH, month);
                start.set(Calendar.DAY_OF_MONTH, day);
                break;
            case DatePickerFragment.CREATION_END_DATE:
                mDatePickerEndButton.getEditText().setText(dateText);
                end.set(Calendar.YEAR, year);
                end.set(Calendar.MONTH, month);
                end.set(Calendar.DAY_OF_MONTH, day);
                break;
            default:
                Log.d("[onDateSet]", "This flag has not been handled.");
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int flag, int hour, int minute) {
        String timeText = CalendarHelper.getPrettyTime(hour, minute);
        switch (flag) {
            case TimePickerFragment.CREATION_START_TIME:
                mTimePickerStartButton.getEditText().setText(timeText);
                start.set(Calendar.HOUR_OF_DAY, hour);
                start.set(Calendar.MINUTE, minute);
                break;
            case TimePickerFragment.CREATION_END_TIME:
                mTimePickerEndButton.getEditText().setText(timeText);
                end.set(Calendar.HOUR_OF_DAY, hour);
                start.set(Calendar.MINUTE, minute);
                break;
            default:
                Log.d("[onTimeSet]", "This flag has not been handled.");
        }
    }
}
