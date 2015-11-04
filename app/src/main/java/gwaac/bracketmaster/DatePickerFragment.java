package gwaac.bracketmaster;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.DatePicker;
import java.util.Calendar;

/**
 * Created by Charlie on 10/30/15.
 */
public class DatePickerFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final int CREATION_START_DATE = 1;
    public static final int CREATION_END_DATE = 2;

    private int mFlag;

    public static DatePickerFragment newInstance(int flag) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", flag);
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;
    }

    public interface OnDateChosenListener {
        public void onDateSet(DatePicker view, int flag, int year, int month, int day);
    }

    private OnDateChosenListener mOnDateChosenListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnDateChosenListener = (OnDateChosenListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDateChosenListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle onSavedInstanceState) {
        mFlag = getArguments().getInt("flag");
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        mOnDateChosenListener.onDateSet(datePicker, mFlag, i, i1, i2);
    }
}
