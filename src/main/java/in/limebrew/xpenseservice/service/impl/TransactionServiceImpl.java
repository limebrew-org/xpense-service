package in.limebrew.xpenseservice.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import in.limebrew.xpenseservice.entity.Transaction;
import in.limebrew.xpenseservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private final Firestore firestore;

    private final String transactionCollection = "transactions";

    public TransactionServiceImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public List<Transaction> getAllTransactions(String profileId) throws ExecutionException, InterruptedException {
        CollectionReference transactions = firestore.collection(transactionCollection);
        Query query = transactions.whereEqualTo("profileId", profileId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        //? Handle if empty
        if (documents.isEmpty()) {
            return Collections.emptyList();
        }

        //? Parse and append in Array
        List<Transaction> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            result.add(document.toObject(Transaction.class));
        }
        return result;
    }

    @Override
    public void getTransactionsByQuery(String profileId,
                                       Date creationDate,
                                       String creationMonth,
                                       int creationYear,
                                       double transactionAmount,
                                       String transactionType,
                                       String transactionTag,
                                       String transactionRemarks){

    }

    @Override
    public void getTransactionsByRange(Date startDate, Date endDate, double startAmount, double endAmount){

    }

    @Override
    public String createTransaction(Transaction transaction) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collectionApiFuture = firestore.collection(transactionCollection).document().set(transaction);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    @Override
    public String updateTransactionById(String id, Transaction transaction) throws ExecutionException, InterruptedException{
        ApiFuture<WriteResult> collectionApiFuture = firestore.collection(transactionCollection).document(id).set(transaction);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    @Override
    public String deleteTransactionById(String id)  throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collectionApiFuture = firestore.collection(transactionCollection).document(id).delete();
        return collectionApiFuture.get().getUpdateTime().toString();
    }

}
