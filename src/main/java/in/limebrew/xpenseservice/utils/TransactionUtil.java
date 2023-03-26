package in.limebrew.xpenseservice.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionUtil {
    public static final List<String> monthList = Arrays.asList("jan","feb","mar","apr","may","jun","jul","aug","sep","oct","nov","dec");
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

//            if(!monthList.stream().findFirst().equals(creationMonth)) return false;
            if(creationMonth != "" && !monthList.stream().findFirst().equals(creationMonth)) return false;

            int parsedCreationYear = Integer.parseInt(creationYear);

                return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

}
