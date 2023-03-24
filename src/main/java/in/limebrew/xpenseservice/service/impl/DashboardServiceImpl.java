package in.limebrew.xpenseservice.service.impl;

import in.limebrew.xpenseservice.repository.TransactionRepository;
import in.limebrew.xpenseservice.service.DashboardService;
import in.limebrew.xpenseservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class DashboardServiceImpl implements DashboardService {
//    @Autowired
//    TransactionRepository transactionRepository;

    @Autowired
    TransactionService transactionService;

    @Override
    public void getOverallDashboard(String profileId){}

    @Override
    public void getDashboardByQuery(String profileId, Date date, String month, int year){}

    @Override
    public void getDashboardByDateRange(String profileId, Date startDate, Date endDate){}
}
