package in.limebrew.xpenseservice.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import in.limebrew.xpenseservice.entity.Transaction;
import in.limebrew.xpenseservice.service.FirebaseAuthService;
import in.limebrew.xpenseservice.service.TransactionService;
import in.limebrew.xpenseservice.utils.DateUtil;
import in.limebrew.xpenseservice.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value="/api/transactions")
@CrossOrigin
public class TransactionController {

    @Autowired
    FirebaseAuthService firebaseAuthService;

    @Autowired
    TransactionService transactionService;

    private static final String limitDefault = "10";
    private static final String exceedLimit = "100";

    public boolean isLimitExceeds(int queryLimit){
        return queryLimit > Integer.parseInt(exceedLimit);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Map<String,Object>> getAllTransactions(@RequestHeader("Authorization") String authHeader,
                                                                 @RequestParam(defaultValue = limitDefault ) int limit) {

        //? Check if Query limit request exceeds
        if(isLimitExceeds(limit))
            return ResponseUtil.errorLimitExceeded();

        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            //? Get all transactions by profile id
            List<Transaction> allTransactionsByProfileId = transactionService.getAllTransactions(decodedToken.getUid(),limit);

            //? Return response
            return ResponseUtil.successGetMany(allTransactionsByProfileId);

        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            return ResponseUtil.errorNotFound();
        }

    }

    @GetMapping(value = "/query")
    public ResponseEntity<?> getTransactionByQuery(@RequestHeader("Authorization") String authHeader,
                                                   @RequestParam(defaultValue = "") String creationDate,
                                                   @RequestParam(defaultValue = "") String creationMonth,
                                                   @RequestParam(defaultValue = "") String creationYear,
                                                   @RequestParam(defaultValue = "") String transactionAmount,
                                                   @RequestParam(defaultValue = "") String transactionType,
                                                   @RequestParam(defaultValue = "") String transactionTag,
                                                   @RequestParam(defaultValue = "") String transactionRemark,
                                                   @RequestParam(defaultValue = limitDefault ) int limit) {

        //? Check if Query limit request exceeds
        if(isLimitExceeds(limit))
            return ResponseUtil.errorLimitExceeded();

        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            //? Get all transactions by profile id
             transactionService.getTransactionsByQuery(
                    decodedToken.getUid(),
                    creationDate,
                    creationMonth,
                    creationYear,
                    transactionAmount,
                    transactionType,
                    transactionTag,
                    transactionRemark,
                    limit);

            //? Return response
            return null;

        } catch (FirebaseAuthException e) {
            return ResponseUtil.errorNotFound();
        }

    }

    @GetMapping(value = "/query/{id}")
    public ResponseEntity<?> getTransactionById(@RequestHeader("Authorization") String authHeader,
                                                @PathVariable("id") String id) {
        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            //? Get all transactions by profile id
            Transaction transactionById = transactionService.getTransactionById(decodedToken.getUid(), id);

            //? Check if transaction was found
            if(transactionById == null) {
                return ResponseUtil.errorNotFound();
            }

            //? Return response
            return ResponseUtil.successGetOne(transactionById);

        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            return ResponseUtil.errorNotFound();
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Map<String,Object>> createTransaction(@RequestHeader("Authorization") String authHeader,
                                               @RequestBody Transaction transaction) {
        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            //? Check if valid date format
            if (!DateUtil.isValidDate(transaction.getCreationDate())) {
                return ResponseUtil.errorParsingEntity("Error! Date format must be in dd-MM-yyyy");
            }

            //? Set the profile id from the JWT
            transaction.setProfileId(decodedToken.getUid());

            //? Save in the database
            transactionService.createTransaction(transaction);

            //? Return response
            return ResponseUtil.successAddOne();

        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            return ResponseUtil.errorUnauthorized();
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Map<String,Object>> updateTransactionById(@RequestHeader("Authorization") String authHeader,
                                                   @PathVariable("id") String id,
                                                   @RequestBody Transaction transaction) {
        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            //? Check if valid date format
            if (!DateUtil.isValidDate(transaction.getCreationDate())) {
                return ResponseUtil.errorParsingEntity("Error! Date format must be in dd-MM-yyyy");
            }

            //? Set the profile id from the JWT
            transaction.setProfileId(decodedToken.getUid());

            //? update in the database
            transactionService.updateTransactionById(id, transaction);

            //? Return response
            return ResponseUtil.successUpdateOne();

        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            return ResponseUtil.errorUnauthorized();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Map<String,Object>> deleteTransactionById(@RequestHeader("Authorization") String authHeader,
                                                   @PathVariable("id") String id) {
        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            //? Delete transaction by id
            transactionService.deleteTransactionById(id);

            //? Return response
            return ResponseUtil.successDeleteOne();

        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            return ResponseUtil.errorUnauthorized();
        }
    }

}
