package in.limebrew.xpenseservice.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import in.limebrew.xpenseservice.entity.Transaction;
import in.limebrew.xpenseservice.service.FirebaseAuthService;
import in.limebrew.xpenseservice.service.TransactionService;
import in.limebrew.xpenseservice.utils.DateUtil;
import in.limebrew.xpenseservice.utils.ResponseUtil;
import in.limebrew.xpenseservice.utils.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
                                                   @RequestParam(defaultValue = "", required = false) String creationDate,
                                                   @RequestParam(defaultValue = "", required = false) String creationMonth,
                                                   @RequestParam(defaultValue = "", required = false) String creationYear,
                                                   @RequestParam(defaultValue = "", required = false) String transactionAmount,
                                                   @RequestParam(defaultValue = "", required = false) String transactionType,
                                                   @RequestParam(defaultValue = "", required = false) String transactionTag,
                                                   @RequestParam(defaultValue = "", required = false) String transactionRemarks,
                                                   @RequestParam(defaultValue = limitDefault, required = false) int limit) {

        //? Check if Query limit request exceeds
        if(isLimitExceeds(limit))
            return ResponseUtil.errorLimitExceeded();

        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            //? Get all transactions by profile id
             List<Transaction> transactionByQuery =  transactionService.getTransactionsByQuery(
                    decodedToken.getUid(),
                    creationDate,
                    creationMonth,
                    creationYear,
                    transactionAmount,
                    transactionType,
                    transactionTag,
                    transactionRemarks,
                    limit);

            //? Return response
            return ResponseUtil.successGetMany(transactionByQuery);

        } catch (FirebaseAuthException | InterruptedException | NullPointerException | ExecutionException e) {
            return ResponseUtil.errorNotFound();
        }

    }

    @GetMapping(value = "/query/range")
    public ResponseEntity<?> getTransactionByQueryRange(@RequestHeader("Authorization") String authHeader,
                                                        @RequestParam(defaultValue = "", required = false) String startDate,
                                                        @RequestParam(defaultValue = "", required = false) String endDate,
                                                        @RequestParam(defaultValue = "", required = false) String creationDate,
                                                        @RequestParam(defaultValue = "", required = false) String creationMonth,
                                                        @RequestParam(defaultValue = "", required = false) String creationYear,
                                                        @RequestParam(defaultValue = "", required = false) String startAmount,
                                                        @RequestParam(defaultValue = "", required = false) String endAmount,
                                                        @RequestParam(defaultValue = "", required = false) String transactionAmount,
                                                        @RequestParam(defaultValue = "", required = false) String transactionType,
                                                        @RequestParam(defaultValue = "", required = false) String transactionTag,
                                                        @RequestParam(defaultValue = "", required = false) String transactionRemarks,
                                                        @RequestParam(defaultValue = limitDefault, required = false) int limit) {
        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            //? Get all transactions by profile id
            List<Transaction> transactionByRange =  transactionService.getTransactionsByRange(
                    decodedToken.getUid(),
                    startDate,
                    endDate,
                    creationDate,
                    creationMonth,
                    creationYear,
                    startAmount,
                    endAmount,
                    transactionAmount,
                    transactionType,
                    transactionTag,
                    transactionRemarks,
                    limit);

            //? Return response
            return ResponseUtil.successGetMany(transactionByRange);

        } catch (FirebaseAuthException | ExecutionException | InterruptedException | RuntimeException | ParseException e) {
            System.out.println(e);
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
            //? Extract the token  Bearer_
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            //? Check if valid date format
            if (!DateUtil.isValidDate(transaction.getCreationDate())) {
                return ResponseUtil.errorParsingEntity("Error! Date format must be in dd-MM-yyyy");
            }

            //? Set the profile id from the JWT
            transaction.setProfileId(decodedToken.getUid());

            //? Set the Month and Year and Timestamp in the entity
            transaction.setCreationMonth(TransactionUtil.getMonthFromDate(transaction.getCreationDate()));
            transaction.setCreationYear(TransactionUtil.getYearFromDate(transaction.getCreationDate()));
            transaction.setCreationTimeStamp(DateUtil.getUnixTimeFromDate(transaction.getCreationDate()));


            //? Save in the database
            transactionService.createTransaction(transaction);

            //? Return response
            return ResponseUtil.successAddOne();

        } catch (FirebaseAuthException | ExecutionException | InterruptedException | ParseException e) {
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

            //? Set the profile id , month and year from the JWT
            transaction.setProfileId(decodedToken.getUid());
            transaction.setCreationTimeStamp(DateUtil.getUnixTimeFromDate(transaction.getCreationDate()));
            transaction.setCreationMonth(TransactionUtil.getMonthFromDate(transaction.getCreationDate()));
            transaction.setCreationYear(TransactionUtil.getYearFromDate(transaction.getCreationDate()));

            //? update in the database
            transactionService.updateTransactionById(id, transaction);

            //? Return response
            return ResponseUtil.successUpdateOne();

        } catch (FirebaseAuthException | ExecutionException | InterruptedException | ParseException e) {
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
