package in.limebrew.xpenseservice.service.impl;

import com.google.cloud.firestore.*;
import in.limebrew.xpenseservice.entity.Transaction;
import in.limebrew.xpenseservice.service.DashboardService;
import in.limebrew.xpenseservice.utils.DateUtil;
import in.limebrew.xpenseservice.utils.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private final Firestore firestore;

    private static double netEarning = 0;
    private static double netExpense = 0;
    private static double netInvestment = 0;
    private static double netFundTransfer = 0;
    private static double netSavings = 0;

    private final String transactionCollection = "transactions";

    public DashboardServiceImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public List<Transaction> getDashboardByQuery(String profileId, String creationDate, String creationMonth, String creationYear)  throws ExecutionException, InterruptedException, ParseException {
        CollectionReference transactionCollectionRef = firestore.collection(transactionCollection);

        //? Query By ProfileId
        Query query = transactionCollectionRef.whereEqualTo("profileId", profileId);

        //? Check if Date was passed
        if(DateUtil.isValidDate(creationDate)){
            query = query.whereEqualTo("creationDate",creationDate);
        }
        //? Check if month was passed -> year
        else if(TransactionUtil.isValidMonth(creationMonth) && TransactionUtil.isValidYear(creationYear)) {
            query = query
                    .whereEqualTo("creationMonth",creationMonth)
                    .whereEqualTo("creationYear",Integer.parseInt(creationYear));

            //? Order by Creation date
            query = query.orderBy("creationDate", Query.Direction.DESCENDING);
        }
        //? Check if year passed
        else if(TransactionUtil.isValidYear(creationYear)){
            query = query.whereEqualTo("creationYear",Integer.parseInt(creationYear));

            //? Order by Creation date
            query = query.orderBy("creationDate", Query.Direction.DESCENDING);
        }
        else {
            return new ArrayList<>();
        }

        //? Query in the db
        QuerySnapshot querySnapshot = query.get().get();
        List<QueryDocumentSnapshot> transactionDocuments = querySnapshot.getDocuments();

        //? Handle if empty
        if (transactionDocuments.isEmpty()) {
            return new ArrayList<>();
        }

        //? Parse documents
        List<Transaction> transactionMap = transactionDocuments.stream()
                .map(queryDocumentSnapshot -> {
                    String amountKey = "transactionAmount";
                    String tagKey = "transactionTag";
                    String typeKey = "transactionType";

                    Map<String,Object> transaction = queryDocumentSnapshot.getData();
                    double transactionAmount = (double) transaction.get(amountKey);
                    String transactionTag = (String) transaction.get(tagKey);
                    String transactionType = (String) transaction.get(typeKey);

                    System.out.println("Amount: "+transactionAmount);
                    System.out.println("Tag: "+transactionTag);
                    System.out.println("Type: "+transactionType);

                    //? Earnings/Income
                    if(transactionTag == "earning"){
                        DashboardServiceImpl.netEarning+=transactionAmount;
                    }
                    //? Normal Expense
                    else if(transactionTag == "expense" && transactionType == "debit"){
                        DashboardServiceImpl.netExpense+=transactionAmount;
                    }
                    //? Refunds/Cashbacks/Discounts
                    else if(transactionTag == "expense" && transactionType == "credit"){
                        DashboardServiceImpl.netExpense-=transactionAmount;
                    }
                    //? Investment
                    else if(transactionTag == "investment" && transactionType == "debit"){
                        DashboardServiceImpl.netInvestment+=transactionAmount;
                    }
                    //? Withdrawal/Claim of Investment
                    else if(transactionTag == "investment" && transactionType == "credit"){
                        DashboardServiceImpl.netInvestment-=transactionAmount;
                    }
                    else if( transactionTag == "fund_transfer"){
                        DashboardServiceImpl.netFundTransfer+=transactionAmount;
                    }

                    System.out.println("Net Earning: "+DashboardServiceImpl.netEarning);
                    System.out.println("Net Expense: "+DashboardServiceImpl.netExpense);
                    System.out.println("Net Investment: "+DashboardServiceImpl.netInvestment);
                    System.out.println("Net Fund Transfer: "+DashboardServiceImpl.netFundTransfer);

                    return queryDocumentSnapshot.toObject(Transaction.class);
                })
                .collect(Collectors.toList());

        return transactionMap;

    }

    @Override
    public void getDashboardByDateRange(String profileId, Date startDate, Date endDate){}
}
