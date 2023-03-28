package in.limebrew.xpenseservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static boolean isValidDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String getUnixTimeFromDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        if(!isValidDate(dateString)) {
            return "";
        }

        Date date = format.parse(dateString);
        long unixTime = date.getTime()/1000L;
        return "" + unixTime;

    }

}
