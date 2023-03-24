package in.limebrew.xpenseservice.service;

import java.util.Date;

public interface TransactionService {
    void getAllTransactions(String profileId);
    void getTransactionsByQuery(String profileId,
                                Date creationDate,
                                String creationMonth,
                                int creationYear,
                                double transactionAmount,
                                String transactionType,
                                String transactionTag,
                                String transactionRemarks);
    void getTransactionsByRange(Date startDate, Date endDate, double startAmount, double endAmount);
}
