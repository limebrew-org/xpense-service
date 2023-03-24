package in.limebrew.xpenseservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

}
