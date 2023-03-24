package in.limebrew.xpenseservice.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import in.limebrew.xpenseservice.entity.Transaction;
import in.limebrew.xpenseservice.service.FirebaseAuthService;
import in.limebrew.xpenseservice.service.TransactionService;
import in.limebrew.xpenseservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value="/api/transactions")
@CrossOrigin
public class TransactionController {

    @Autowired
    FirebaseAuthService firebaseAuthService;

    @Autowired
    TransactionService transactionService;

    private final String itemCountDefault = "10";
    private final String itemCountLimit = "100";

    @GetMapping(value = "/all")
    public List<Transaction> getAllTransactions(@RequestHeader("Authorization") String authHeader,
                                                @RequestParam(defaultValue = itemCountDefault ) int itemCount) {

        List<Transaction> allTransactionsByProfileId = new ArrayList<>();
        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            System.out.println("profileId: "+ decodedToken.getUid());

            //? Get all transactions by profile id
            allTransactionsByProfileId = transactionService.getAllTransactions(decodedToken.getUid());

            return allTransactionsByProfileId;


        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            return allTransactionsByProfileId;
        }

    }

    @GetMapping(value = "/query")
    public ResponseEntity<?> getTransactionByQuery(@RequestHeader("Authorization") String authHeader,
                                                   @RequestParam(defaultValue = "") Date creationDate,
                                                   @RequestParam(defaultValue = "") String creationMonth,
                                                   @RequestParam(defaultValue = "") int creationYear,
                                                   @RequestParam(defaultValue = "") double transactionAmount,
                                                   @RequestParam(defaultValue = "") String transactionType,
                                                   @RequestParam(defaultValue = "") String transactionTag,
                                                   @RequestParam(defaultValue = "") String transactionRemark) {

        return null;
    }

    @GetMapping(value = "/query/{id}")
    public ResponseEntity<?> getTransactionById(@RequestHeader("Authorization") String authHeader,
                                                @PathVariable("id") String id) {
        return null;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createTransaction(@RequestHeader("Authorization") String authHeader,
                                               @RequestBody Transaction transaction) {
        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            System.out.println("profileId: "+ decodedToken.getUid());

            //? create the payload
            System.out.println("request body");
            System.out.println(transaction);

            if (!DateUtil.isValidDate(transaction.getCreationDate())) {
                return null;
            }

            //? Set the profile id from the JWT
            transaction.setProfileId(decodedToken.getUid());

            //? Save in the database
            transactionService.createTransaction(transaction);

        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>("Unauthenticated!! Invalid token", HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateTransactionById(@RequestHeader("Authorization") String authHeader,
                                                   @PathVariable("id") String id,
                                                   @RequestBody Transaction transaction) {
        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            System.out.println("profileId: "+ decodedToken.getUid());

            //? create the payload
            System.out.println("request body");
            System.out.println(transaction);

            if (!DateUtil.isValidDate(transaction.getCreationDate())) {
                return null;
            }

            //? Set the profile id from the JWT
            transaction.setProfileId(decodedToken.getUid());

            //? update in the database
            transactionService.updateTransactionById();

        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>("Unauthenticated!! Invalid token", HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteTransactionById(@RequestHeader("Authorization") String authHeader,
                                                   @PathVariable("id") String id) {
        return null;
    }

}
