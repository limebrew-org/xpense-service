package in.limebrew.xpenseservice.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import in.limebrew.xpenseservice.entity.Transaction;
import in.limebrew.xpenseservice.service.DashboardService;
import in.limebrew.xpenseservice.service.FirebaseAuthService;
import in.limebrew.xpenseservice.utils.DateUtil;
import in.limebrew.xpenseservice.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value="/api/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    FirebaseAuthService firebaseAuthService;

    @Autowired
    DashboardService dashboardService;

    @GetMapping(value="/query")
    public ResponseEntity<?> getDashBoardByQuery(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam(defaultValue = "", required = false) String creationDate,
                                                 @RequestParam(defaultValue = "",required = false) String creationMonth,
                                                 @RequestParam(defaultValue = "",required = false) String creationYear) {
        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            //? Get Dashboard By Query for a profile
            List<Transaction> dashboardByQuery = dashboardService.getDashboardByQuery(
                    decodedToken.getUid(),
                    creationDate,
                    creationMonth,
                    creationYear
            );

            //? Return response
            return ResponseUtil.successGetMany(dashboardByQuery);

        } catch (FirebaseAuthException | ExecutionException | InterruptedException | ParseException e) {
            System.out.println("Error: "+e);
            return new ResponseEntity<>("Unauthenticated!! Invalid token", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/range")
    public ResponseEntity<?> getDashBoardByDateRange(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam(defaultValue = "") Date startDate,
                                                 @RequestParam(defaultValue = "") Date endDate) {
        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            System.out.println("profileId: "+ decodedToken.getUid());


        } catch (FirebaseAuthException e) {
            return new ResponseEntity<>("Unauthenticated!! Invalid token", HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

}
