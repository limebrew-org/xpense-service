package in.limebrew.xpenseservice.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    @DocumentId
    public String id;

    public String profileId;

    public Date creationDate;

    public String creationMonth;

    public int creationYear;

    public double transactionAmount;

    public String transactionType;

    public String transactionTag;

    public String transactionRemarks;
}
