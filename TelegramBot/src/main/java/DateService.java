import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateService {
    public static String getDate() {
        DateFormat sdf = new SimpleDateFormat(Constants.dateFormat);
        Date date = new Date();
        return sdf.format(date) + addingServerDate;
    }

    public static String getTime() {
        DateFormat sdf = new SimpleDateFormat(Constants.timeFormat);
        Date date = new Date();
        return sdf.format(date) + addingServerTime;
    }

    private static final String addingServerDate = "\n(server date)";
    private static final String addingServerTime = "\n(server time)";
}
