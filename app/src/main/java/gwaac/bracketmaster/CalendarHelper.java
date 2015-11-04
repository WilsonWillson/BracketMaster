package gwaac.bracketmaster;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Charlie on 11/1/15.
 */
public class CalendarHelper {

    private Map<Integer, String> mMonthLabels;

    public CalendarHelper() {
        mMonthLabels = new HashMap<>();
        mMonthLabels.put(0, "January");
        mMonthLabels.put(1, "February");
        mMonthLabels.put(2, "March");
        mMonthLabels.put(3, "April");
        mMonthLabels.put(4, "May");
        mMonthLabels.put(5, "June");
        mMonthLabels.put(6, "July");
        mMonthLabels.put(7, "August");
        mMonthLabels.put(8, "September");
        mMonthLabels.put(9, "October");
        mMonthLabels.put(10, "November");
        mMonthLabels.put(11, "December");
    }

    private String getDayNotation(int day) {
        if (day % 10 == 1 && day / 10 != 1) {
            return "st";
        } else if (day % 10 == 2 && day / 10 != 1) {
            return "nd";
        } else if (day % 10 == 3 && day / 10 != 1) {
            return "rd";
        } else {
            return "th";
        }
    }

    public String getPrettyTime(int hour, int minute) {
        return (hour % 12 == 0 ? "12" : hour % 12) + ":" + (minute < 10 ? "0" + minute : minute) + " " + (hour < 12 ? "AM" : "PM");
    }

    public String getPrettyDateTime(Calendar date) {
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);

        return mMonthLabels.get(month) + " " + day + getDayNotation(day) + " @ " + getPrettyTime(hour, minute);
    }

    public String getPrettyDate(int year, int month, int day) {
        return mMonthLabels.get(month) + " " + day + getDayNotation(day) + ", " + year;
    }

}
