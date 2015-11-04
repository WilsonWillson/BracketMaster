package gwaac.bracketmaster;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class CreationActivity extends AppCompatActivity implements DatePickerFragment.OnDateChosenListener, TimePickerFragment.OnTimeChosenListener{

    private EditText mTournamentNameField;
    private EditText mGameNameField;
    private EditText mDescriptionField;

    private TextInputLayout mDatePickerStartButton;
    private TextInputLayout mDatePickerEndButton;
    private TextInputLayout mTimePickerStartButton;
    private TextInputLayout mTimePickerEndButton;

    private Button mCreateButton;

    private Notifier notifier;

    private CalendarHelper mCalendarHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        notifier = new Notifier(this);
        mCalendarHelper = new CalendarHelper();

        mTournamentNameField = (EditText) findViewById(R.id.tournament_name);
        mGameNameField = (EditText) findViewById(R.id.tournament_game);
        mDescriptionField = (EditText) findViewById(R.id.description);

        mDatePickerStartButton = (TextInputLayout) findViewById(R.id.tournament_date_start);
        if (mDatePickerStartButton.getEditText() != null) {
            mDatePickerStartButton.getEditText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment dateFragment = DatePickerFragment.newInstance(DatePickerFragment.CREATION_START_DATE);
                    dateFragment.show(getSupportFragmentManager(), "startDate");
                }
            });
        }

        mDatePickerEndButton = (TextInputLayout)findViewById(R.id.tournament_date_end);
        if (mDatePickerEndButton.getEditText() != null) {
            mDatePickerEndButton.getEditText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment dateFragment = DatePickerFragment.newInstance(DatePickerFragment.CREATION_END_DATE);
                    dateFragment.show(getSupportFragmentManager(), "endDate");
                }
            });
        }

        mTimePickerStartButton = (TextInputLayout)findViewById(R.id.tournament_time_start);
        if (mTimePickerStartButton.getEditText() != null) {
            mTimePickerStartButton.getEditText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment timeFragment = TimePickerFragment.newInstance(TimePickerFragment.CREATION_START_TIME);
                    timeFragment.show(getSupportFragmentManager(), "startTime");
                }
            });
        }

        mTimePickerEndButton = (TextInputLayout)findViewById(R.id.tournament_time_end);
        if (mTimePickerEndButton.getEditText() != null) {
            mTimePickerEndButton.getEditText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment timeFragment = TimePickerFragment.newInstance(TimePickerFragment.CREATION_END_TIME);
                    timeFragment.show(getSupportFragmentManager(), "endTime");
                }
            });
        }

        mCreateButton = (Button) findViewById(R.id.create_tournament);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifier.setView(v);
                // TODO: submit stuff.
                finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int flag, int year, int month, int day) {
        String dateText = mCalendarHelper.getPrettyDate(year, month, day);
        if (flag == DatePickerFragment.CREATION_START_DATE) {
            mDatePickerStartButton.getEditText().setText(dateText);
        } else if (flag == DatePickerFragment.CREATION_END_DATE) {
            mDatePickerEndButton.getEditText().setText(dateText);
        } else {
            Log.d("[onDateSet]", "This flag has not been handled.");
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int flag, int hour, int minute) {
        String timeText = mCalendarHelper.getPrettyTime(hour, minute);
        if (flag == TimePickerFragment.CREATION_START_TIME) {
            mTimePickerStartButton.getEditText().setText(timeText);
        } else if (flag == TimePickerFragment.CREATION_END_TIME) {
            mTimePickerEndButton.getEditText().setText(timeText);
        } else {
            Log.d("[onTimeSet]", "This flag has not been handled.");
        }
    }
}
