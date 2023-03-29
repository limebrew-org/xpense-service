package in.limebrew.xpenseservice.query;

import com.google.cloud.firestore.Query;
import in.limebrew.xpenseservice.utils.DateUtil;
import in.limebrew.xpenseservice.utils.TransactionUtil;

import java.text.ParseException;
import java.util.Arrays;


public class TransactionQueryBuilder {
    public static Query buildQueryByTime(Query query, String date, String month, String year) {
        System.out.println("Inside build query by time");
        //? Check if Date was passed
        if(DateUtil.isValidDate(date)){
            System.out.println("Inside date");
            query = query.whereEqualTo("creationDate",date);
        }

        //? Check if month was passed -> year
        else if(TransactionUtil.isValidMonth(month) && TransactionUtil.isValidYear(year)) {
            System.out.println("Inside month and year");
            query = query
                    .whereEqualTo("creationMonth",month)
                    .whereEqualTo("creationYear",Integer.parseInt(year));
        }

        //? Check if year passed
        else if(TransactionUtil.isValidYear(year)){
            System.out.println("Inside year");
            query = query.whereEqualTo("creationYear",Integer.parseInt(year));
        }
        else {
            throw new NullPointerException("A valid date/month/year must be passed");
        }
        return query;
    }

    public static Query buildQueryByTimeRange(Query query,
                                              String startDate,
                                              String endDate,
                                              String creationDate,
                                              String creationMonth,
                                              String creationYear) throws ParseException, NullPointerException {
        System.out.println("Inside build query by time range");
        //? Check if startDate and endDate is passed and valid
        if(DateUtil.isValidDate(startDate) && DateUtil.isValidDate(endDate)) {
            query = query.whereGreaterThan("creationTimeStamp", DateUtil.getUnixTimeFromDate(startDate))
                    .whereLessThan("creationTimeStamp", DateUtil.getUnixTimeFromDate(endDate));
        }
        else {

            query = TransactionQueryBuilder.buildQueryByTime(query, creationDate, creationMonth, creationYear);
        }
        System.out.println("End of build query by time range");
        return query;
    }

    public static Query buildQueryByTransaction(Query timeQuery, String amount, String type, String tag, String remarks) {
        System.out.println("Inside build transaction query");
        //? Check if amount is passed and valid
        if(TransactionUtil.idValidAmount(amount)) {
            System.out.println("Inside amount");
            timeQuery = timeQuery.whereEqualTo("transactionAmount",Double.parseDouble(amount));
        }

        if(type != null && type.length()>0) {
            System.out.println("Inside type");
            timeQuery = timeQuery.whereEqualTo("transactionType", type);
        }

        if(tag != null && tag.length()>0) {
            System.out.println("Inside tag");
            timeQuery = timeQuery.whereEqualTo("transactionTag", tag);
        }

        if(remarks != null && remarks.length()>0) {
            System.out.println("Inside remarks");
            //? Check if the substring is part of the transaction remark
            timeQuery = timeQuery.whereIn("transactionRemarks", Arrays.asList(remarks));
        }

        System.out.println("end of transaction query");
        return timeQuery;
    }

    public static Query buildTransactionQueryByAmountRange(Query query,
                                                     String startAmount,
                                                     String endAmount,
                                                     String transactionAmount
                                                     ) {
        System.out.println("Inside build query by amount range");
        //? Check if startAmount and endAmount is passed and valid
        if(TransactionUtil.idValidAmount(startAmount) && TransactionUtil.idValidAmount(endAmount)) {
            query = query.whereGreaterThan("transactionAmount", Double.parseDouble(startAmount))
                         .whereLessThan("transactionAmount", Double.parseDouble(endAmount));
        }

        else if (TransactionUtil.idValidAmount(transactionAmount)) {
            query = query.whereEqualTo("transactionAmount", Double.parseDouble(transactionAmount));
        }

        return query;
    }

    public static Query buildQueryByTransactionRange(Query query,
                                                     String startAmount,
                                                     String endAmount,
                                                     String transactionAmount,
                                                     String transactionType,
                                                     String transactionTag,
                                                     String transactionRemarks) {
        System.out.println("Inside build query by transaction range");
        //? Build query by amount range
        query = TransactionQueryBuilder.buildTransactionQueryByAmountRange(query, startAmount, endAmount, transactionAmount);

        if(transactionType != null && transactionType.length()>0) {
            System.out.println("Inside type");
            query = query.whereEqualTo("transactionType", transactionType);
        }

        if(transactionTag != null && transactionTag.length()>0) {
            System.out.println("Inside tag");
            query = query.whereEqualTo("transactionTag", transactionTag);
        }

        if(transactionRemarks != null && transactionRemarks.length()>0) {
            System.out.println("Inside remarks");
            //? Check if the substring is part of the transaction remark
            query = query.whereIn("transactionRemarks", Arrays.asList(transactionRemarks));
        }
        return query;
    }


}
