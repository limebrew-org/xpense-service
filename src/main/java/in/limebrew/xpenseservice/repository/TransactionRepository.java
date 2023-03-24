package in.limebrew.xpenseservice.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Date;

@Repository
public interface TransactionRepository {
    ArrayList<DocumentSnapshot> getAll();
    DocumentSnapshot getById(String id);
    ArrayList<DocumentSnapshot> getByProfileId(String profileId);
    ArrayList<DocumentSnapshot> getByCreationDate(Date creationDate);
    ArrayList<DocumentSnapshot> getByCreationMonth(String creationMonth);
    ArrayList<DocumentSnapshot> getByCreationYear(int creationYear);
    ArrayList<DocumentSnapshot> getByTransactionAmount(double creationAmount);
    ArrayList<DocumentSnapshot> getByTransactionType(String transactionType);
    ArrayList<DocumentSnapshot> getByTransactionTag(String transactionTag);
    ArrayList<DocumentSnapshot> getByTransactionRemarks(String transactionRemarks);
}
