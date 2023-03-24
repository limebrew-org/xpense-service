package in.limebrew.xpenseservice.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import in.limebrew.xpenseservice.service.FirebaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
@RequestMapping(value="/api/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    FirebaseAuthService firebaseAuthService;

    @GetMapping(value="/overall")
    public ResponseEntity<?> getOverallDashboard(@RequestHeader("Authorization") String authHeader) {

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

    @GetMapping(value="/query")
    public ResponseEntity<?> getDashBoardByQuery(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam(defaultValue = "") Date date,
                                                 @RequestParam(defaultValue = "") String month,
                                                 @RequestParam(defaultValue = "") int year) {
        try {
            //? Extract the token
            String token = authHeader.substring(7);

            //? Verify the JWT
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            System.out.println("token: "+ decodedToken.getUid());


        } catch (FirebaseAuthException e) {
            return new ResponseEntity<>("Unauthenticated!! Invalid token", HttpStatus.UNAUTHORIZED);
        }
        return null;
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
