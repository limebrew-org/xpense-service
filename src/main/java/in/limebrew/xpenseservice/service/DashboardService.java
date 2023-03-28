package in.limebrew.xpenseservice.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface DashboardService {
    Map<String,Object> getDashboardByQuery(String profileId, String creationDate, String creationMonth, String creationYear) throws ExecutionException, InterruptedException, ParseException;
    void getDashboardByDateRange(String profileId, Date startDate, Date endDate);
}
