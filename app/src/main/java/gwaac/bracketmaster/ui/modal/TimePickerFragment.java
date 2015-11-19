package gwaac.bracketmaster.ui.modal;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Charlie on 11/1/15.
 */
public class TimePickerFragment extends AppCompatDialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final int CREATION_START_TIME = 1;
    public static final int CREATION_END_TIME = 2;

    private int mFlag;

    public static TimePickerFragment newInstance(int flag) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", flag);
        timePickerFragment.setArguments(bundle);
        return timePickerFragment;
    }

    public interface OnTimeChosenListener {
        public void onTimeSet(TimePicker view, int flag, int hour, int minute);
    }

    private OnTimeChosenListener mOnTimeChosenListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnTimeChosenListener = (OnTimeChosenListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTimeChosenListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle onSavedInstanceState) {
        mFlag = getArguments().getInt("flag");
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        mOnTimeChosenListener.onTimeSet(timePicker, mFlag, i, i1);
    }
}
