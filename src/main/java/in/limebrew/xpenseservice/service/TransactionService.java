package in.limebrew.xpenseservice.service;

import com.google.cloud.firestore.DocumentSnapshot;
import in.limebrew.xpenseservice.entity.Transaction;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public interface TransactionService {
    List<Transaction> getAllTransactions(String profileId, int limit) throws ExecutionException, InterruptedException;
    List<Transaction> getTransactionsByQuery(String profileId,
                                String creationDate,
                                String creationMonth,
                                String creationYear,
                                String transactionAmount,
                                String transactionType,
                                String transactionTag,
                                String transactionRemarks,
                                int limit) throws ExecutionException, InterruptedException, NullPointerException;
    List<Transaction> getTransactionsByRange(String profileId,
                                String startDate,
                                String endDate,
                                String creationDate,
                                String creationMonth,
                                String creationYear,
                                String startAmount,
                                String endAmount,
                                String transactionAmount,
                                String transactionType,
                                String transactionTag,
                                String transactionRemarks,
                                int limit) throws ExecutionException, InterruptedException, NullPointerException, ParseException;

    Transaction getTransactionById(String profileId ,String id) throws ExecutionException, InterruptedException;

    String createTransaction(Transaction transaction) throws ExecutionException, InterruptedException;

    String updateTransactionById(String id, Transaction transaction) throws ExecutionException, InterruptedException;

    String deleteTransactionById(String id) throws ExecutionException, InterruptedException;
}
