package in.limebrew.xpenseservice.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import in.limebrew.xpenseservice.entity.Transaction;
import in.limebrew.xpenseservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private final Firestore firestore;

    private final String transactionCollection = "transactions";

    public TransactionServiceImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public List<Transaction> getAllTransactions(String profileId, int limit) throws ExecutionException, InterruptedException {
        CollectionReference transactions = firestore.collection(transactionCollection);

        //? Filter By ProfileId and sort by CreationDate (requires composite index)
        Query query = transactions
                .whereEqualTo("profileId", profileId)
                .orderBy("creationDate", Query.Direction.DESCENDING)
                .limit(limit);

        QuerySnapshot querySnapshot = query.get().get();
        List<QueryDocumentSnapshot> transactionDocuments = querySnapshot.getDocuments();

        //? Handle if empty
        if (transactionDocuments.isEmpty()) {
            return Collections.emptyList();
        }

        //? Parse documents
        return transactionDocuments.stream()
                .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(Transaction.class))
                .collect(Collectors.toList());
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
    public Transaction getTransactionById(String profileId, String id) throws ExecutionException, InterruptedException {
        CollectionReference transactions = firestore.collection(transactionCollection);
        DocumentReference transactionDocument = transactions.document(id);
        DocumentSnapshot transactionSnapshot = transactionDocument.get().get();

        //? Check if the id belongs to the profile id
        if(transactionSnapshot.exists() && transactionSnapshot.getString("profileId").equals(profileId))
            return transactionSnapshot.toObject(Transaction.class);

        return null;
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
