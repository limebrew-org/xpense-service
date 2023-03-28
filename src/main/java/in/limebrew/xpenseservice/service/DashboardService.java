package in.limebrew.xpenseservice.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface DashboardService {
    Map<String,Object> getDashboardByQuery(String profileId, String creationDate, String creationMonth, String creationYear) throws ExecutionException, InterruptedException, ParseException;
    Map<String, Object> getDashboardByDateRange(String profileId, String startDate, String endDate) throws ExecutionException, InterruptedException, ParseException;
}
