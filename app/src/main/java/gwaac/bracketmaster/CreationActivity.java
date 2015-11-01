package gwaac.bracketmaster;

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

    private Button mDatePickerStartButton;
    private Button mDatePickerEndButton;
    private Button mTimePickerStartButton;
    private Button mTimePickerEndButton;

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

        mDatePickerStartButton = (Button) findViewById(R.id.datePickerStart);
        mDatePickerStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateFragment = DatePickerFragment.newInstance(DatePickerFragment.CREATION_START_DATE);
                dateFragment.show(getSupportFragmentManager(), "startDate");
            }
        });

        mDatePickerEndButton = (Button)findViewById(R.id.datePickerEnd);
        mDatePickerEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateFragment = DatePickerFragment.newInstance(DatePickerFragment.CREATION_END_DATE);
                dateFragment.show(getSupportFragmentManager(), "endDate");
            }
        });

        mTimePickerStartButton = (Button)findViewById(R.id.timePickerStart);
        mTimePickerStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timeFragment = TimePickerFragment.newInstance(TimePickerFragment.CREATION_START_TIME);
                timeFragment.show(getSupportFragmentManager(), "startTime");
            }
        });

        mTimePickerEndButton = (Button)findViewById(R.id.timePickerEnd);
        mTimePickerEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timeFragment = TimePickerFragment.newInstance(TimePickerFragment.CREATION_END_TIME);
                timeFragment.show(getSupportFragmentManager(), "endTime");
            }
        });

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
        Log.d("[onDateSet]", "Date: " + month + "/" + day + "/" + year + ", Flag: " + flag);
        String dateText = month + "/" + day + "/" + year;
        if (flag == DatePickerFragment.CREATION_START_DATE) {
            mDatePickerStartButton.setText(dateText);
        } else if (flag == DatePickerFragment.CREATION_END_DATE) {
            mDatePickerEndButton.setText(dateText);
        } else {
            Log.d("[onDateSet]", "This flag has not been handled.");
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int flag, int hour, int minute) {
        Log.d("[onTimeSet]", "Time: " + hour + ":" + (minute < 10 ? "0" + minute : minute) + ", Flag: " + flag);
        String timeText = (hour % 12 == 0 ? "12" : hour % 12) + ":" + (minute < 10 ? "0" + minute : minute) + " " + (hour < 12 ? "AM" : "PM");
        if (flag == TimePickerFragment.CREATION_START_TIME) {
            mTimePickerStartButton.setText(timeText);
        } else if (flag == TimePickerFragment.CREATION_END_TIME) {
            mTimePickerEndButton.setText(timeText);
        } else {
            Log.d("[onTimeSet]", "This flag has not been handled.");
        }
    }
}
