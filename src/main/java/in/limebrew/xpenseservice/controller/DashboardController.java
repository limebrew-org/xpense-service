package in.limebrew.xpenseservice.controller;

import in.limebrew.xpenseservice.service.FirebaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return null;
    }

    @GetMapping(value="/query")
    public ResponseEntity<?> getDashBoardByQuery(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam(defaultValue = "") Date date,
                                                 @RequestParam(defaultValue = "") String month,
                                                 @RequestParam(defaultValue = "") int year) {
        return null;
    }

    @GetMapping(value = "/range")
    public ResponseEntity<?> getDashBoardByRange(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam(defaultValue = "") Date startDate,
                                                 @RequestParam(defaultValue = "") Date endDate) {
        return null;
    }

}
