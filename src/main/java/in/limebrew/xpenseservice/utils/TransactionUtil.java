package in.limebrew.xpenseservice.utils;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import in.limebrew.xpenseservice.entity.Transaction;
import in.limebrew.xpenseservice.service.impl.DashboardServiceImpl;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionUtil {
    public static final List<String> monthList = Arrays.asList("jan","feb","mar","apr","may","jun","jul","aug","sep","oct","nov","dec");

    public static int getYearFromDate(String date) throws ParseException, IndexOutOfBoundsException {
        //? dd-mm-yyyy
        return Integer.parseInt(date.substring(6));
    }

    public static String getMonthFromDate(String date) throws ParseException, IndexOutOfBoundsException {
        //? dd-mm-yyyy
        int monthIndex = Integer.parseInt(date.substring(3,5));
        return TransactionUtil.monthList.get(monthIndex-1);
    }

    public static boolean isValidMonth(String month){
        if(month == "" || !TransactionUtil.monthList.contains(month)) return false;
        else return true;
    }

    public static boolean isValidYear(String year){
        try {
            int parsedCreationYear = Integer.parseInt(year);
            if(parsedCreationYear<2020) return false;
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean isValidFields (
                                        String creationDate,
                                        String creationMonth,
                                        String creationYear,
                                        String transactionAmount,
                                        String transactionType,
                                        String transactionTag,
                                        String transactionRemarks) throws ParseException, NumberFormatException {
        try {
            if(!DateUtil.isValidDate(creationDate)) return false;
            if(!TransactionUtil.isValidMonth(creationMonth)) return false;
            if(!TransactionUtil.isValidYear(creationYear)) return false;

                return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

}