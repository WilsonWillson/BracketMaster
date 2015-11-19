package gwaac.bracketmaster.data.helper;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Charlie on 11/1/15.
 */
public class CalendarHelper {

    public static String getPrettyTime(int hour, int minute) {
        Calendar c = new GregorianCalendar(0, 0, 0, hour, minute);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        return timeFormat.format(c.getTime());
    }

    public static String getPrettyDateTime(Calendar date) {
        DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
        return dateTimeFormat.format(date.getTime());
    }

    public static String getPrettyDate(int year, int month, int day) {
        Calendar c = new GregorianCalendar(year, month, day);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        return dateFormat.format(c.getTime());
    }

}
