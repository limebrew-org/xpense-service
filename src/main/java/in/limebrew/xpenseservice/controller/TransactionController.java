package in.limebrew.xpenseservice.controller;

import in.limebrew.xpenseservice.entity.Transaction;
import in.limebrew.xpenseservice.service.FirebaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value="/api/transactions")
@CrossOrigin
public class TransactionController {

    @Autowired
    FirebaseAuthService firebaseAuthService;

    private final String itemCountDefault = "10";
    private final String itemCountLimit = "100";

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllTransactions(@RequestHeader("Authorization") String authHeader,
                                                @RequestParam(defaultValue = itemCountDefault ) int itemCount) {
        if(itemCount>10){
            System.out.println("Limit Exceeded");
        }
        return null;
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
        return null;
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateTransactionById(@RequestHeader("Authorization") String authHeader,
                                                   @PathVariable("id") String id,
                                                   @RequestBody Transaction transaction) {
        return null;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteTransactionById(@RequestHeader("Authorization") String authHeader,
                                                   @PathVariable("id") String id) {
        return null;
    }

}
