package gwaac.bracketmaster.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import gwaac.bracketmaster.ui.modal.Notifier;
import gwaac.bracketmaster.ui.modal.TimePickerFragment;

public class CreationActivity extends AppCompatActivity implements DatePickerFragment.OnDateChosenListener, TimePickerFragment.OnTimeChosenListener {

    @Bind(R.id.tournament_name) EditText mTournamentNameField;
    @Bind(R.id.description) EditText mDescriptionField;

    @Bind(R.id.tournament_game) TextInputLayout mGameSpinnerButton;
    @Bind(R.id.tournament_date_start) TextInputLayout mDatePickerStartButton;
    @Bind(R.id.tournament_date_end) TextInputLayout mDatePickerEndButton;
    @Bind(R.id.tournament_time_start) TextInputLayout mTimePickerStartButton;
    @Bind(R.id.tournament_time_end) TextInputLayout mTimePickerEndButton;

    @Bind(R.id.create_tournament) Button mCreateButton;

    private GameImageLoader mGameImageLoader;
    private Notifier mNotifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);
        mNotifier = new Notifier(this, mCreateButton);

        mGameImageLoader = new GameImageLoader(this);

        if (mGameSpinnerButton.getEditText() != null) {
            mGameSpinnerButton.getEditText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Choose Game");
                    final String[] games = getResources().getStringArray(R.array.game_titles);
                    builder.setItems(games, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            mGameSpinnerButton.getEditText().setText(games[i]);
                        }
                    });
                    builder.show();
                }
            });
        }

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
                String gameName = mGameSpinnerButton.getEditText().getText().toString();
                String description = mDescriptionField.getText().toString();
                String owner = PreferenceManager.getDefaultSharedPreferences(CreationActivity.this).getString("uid", "null");
                if (owner.equals("null")) Log.i("[submitting tourney]", "Nobody's logged in.");
                if (!validateInputs()) {
                    return;
                }
                Tournament tournament = new Tournament()
                        .setOwner(owner)
                        .setDescription(description)
                        .setName(tournamentName)
                        .setGameImageID(mGameImageLoader.getIdForGameName(gameName))
                        .setStartDateTime(start)
                        .setEndDateTime(end);
                Log.i("[submitting tourney]", "Oh no. What's going wrong?");
                CreationActivity.this.submitToFirebase(tournament);
                finish();
            }
        });
    }

    private void submitToFirebase(Tournament t) {
        mCreateButton.setEnabled(false);
        ProgressDialog progressDialog = new ProgressDialog(CreationActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();


        TournamentProperties tp = TournamentProperties.fromTournament(t);
        Firebase myFirebaseRef = ((BracketMasterApplication) getApplicationContext()).myFirebaseRef;
        myFirebaseRef.child("tournaments").push().setValue(tp);

        progressDialog.dismiss();
        mCreateButton.setEnabled(true);

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

    private boolean validateInputs()
    {
        String tournamentName = mTournamentNameField.getText().toString();
        String gameName = mGameSpinnerButton.getEditText().getText().toString();
        String description = mDescriptionField.getText().toString();
        if (tournamentName.length() == 0) {
            mNotifier.alertWithConfirmation("The tournament name is incomplete.");
            return false;
        }
        if (description.length() == 0) {
            mNotifier.alertWithConfirmation("The description is incomplete.");
            return false;
        }
        if (gameName.length() == 0) {
            mNotifier.alertWithConfirmation("You must choose a type of game.");
            return false;
        }
        if (mDatePickerStartButton.getEditText().getText().length() == 0 || mTimePickerStartButton.getEditText().getText().length() == 0) {
            mNotifier.alertWithConfirmation("The start time is not set.");
            return false;
        }
        if (mDatePickerEndButton.getEditText().getText().length() == 0 || mTimePickerEndButton.getEditText().getText().length() == 0) {
            mNotifier.alertWithConfirmation("The end time is not set.");
            return false;
        }
        if (start.after(Calendar.getInstance())) {
            mNotifier.alertWithConfirmation("Your game can't start in the past, silly willy.");
            return false;
        }
        if (!end.after(start)) {
            mNotifier.alertWithConfirmation("The end time must be after the start time.");
            return false;
        }


        return true;

    }
}
