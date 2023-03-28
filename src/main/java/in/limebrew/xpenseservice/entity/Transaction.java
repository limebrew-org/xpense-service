package in.limebrew.xpenseservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    public String creationDate;

    public String creationTimeStamp;

    public String creationMonth;

    public int creationYear;

    public double transactionAmount;

    public String transactionType;

    public String transactionTag;

    public String transactionRemarks;
}
