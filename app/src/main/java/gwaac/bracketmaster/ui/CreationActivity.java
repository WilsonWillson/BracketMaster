package gwaac.bracketmaster.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import butterknife.Bind;
import butterknife.ButterKnife;
import gwaac.bracketmaster.data.helper.CalendarHelper;
import gwaac.bracketmaster.ui.modal.DatePickerFragment;
import gwaac.bracketmaster.R;
import gwaac.bracketmaster.ui.modal.TimePickerFragment;

public class CreationActivity extends AppCompatActivity implements DatePickerFragment.OnDateChosenListener, TimePickerFragment.OnTimeChosenListener {

    @Bind(R.id.tournament_name) EditText mTournamentNameField;
    @Bind(R.id.tournament_game) EditText mGameNameField;
    @Bind(R.id.description) EditText mDescriptionField;

    @Bind(R.id.tournament_date_start) TextInputLayout mDatePickerStartButton;
    @Bind(R.id.tournament_date_end) TextInputLayout mDatePickerEndButton;
    @Bind(R.id.tournament_time_start) TextInputLayout mTimePickerStartButton;
    @Bind(R.id.tournament_time_end) TextInputLayout mTimePickerEndButton;

    @Bind(R.id.create_tournament) Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);

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
                // TODO: submit stuff.
                finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int flag, int year, int month, int day) {
        String dateText = CalendarHelper.getPrettyDate(year, month, day);
        switch (flag) {
            case DatePickerFragment.CREATION_START_DATE:
                mDatePickerStartButton.getEditText().setText(dateText);
                break;
            case DatePickerFragment.CREATION_END_DATE:
                mDatePickerEndButton.getEditText().setText(dateText);
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
                break;
            case TimePickerFragment.CREATION_END_TIME:
                mTimePickerEndButton.getEditText().setText(timeText);
                break;
            default:
                Log.d("[onTimeSet]", "This flag has not been handled.");
        }
    }
}
