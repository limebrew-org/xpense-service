package in.limebrew.xpenseservice.service;

import in.limebrew.xpenseservice.entity.Transaction;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface DashboardService {
    List<Transaction> getDashboardByQuery(String profileId, String creationDate, String creationMonth, String creationYear) throws ExecutionException, InterruptedException, ParseException;
    void getDashboardByDateRange(String profileId, Date startDate, Date endDate);
}
