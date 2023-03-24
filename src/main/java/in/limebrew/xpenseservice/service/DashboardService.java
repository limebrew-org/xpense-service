package in.limebrew.xpenseservice.service;

import java.util.Date;

public interface DashboardService {
    void getOverallDashboard(String profileId);
    void getDashboardByQuery(String profileId, Date date, String month, int year);
    void getDashboardByDateRange(String profileId, Date startDate, Date endDate);
}
