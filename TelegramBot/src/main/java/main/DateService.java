package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateService {
    public static String getDate() {
        DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = new Date();
        return sdf.format(date) + ADDING_SERVER_DATE;
    }

    public static String getTime() {
        DateFormat sdf = new SimpleDateFormat(Constants.TIME_FORMAT);
        Date date = new Date();
        return sdf.format(date) + ADDING_SERVER_TIME;
    }

    private static final String ADDING_SERVER_DATE = "\n(server date)";
    private static final String ADDING_SERVER_TIME = "\n(server time)";
}
