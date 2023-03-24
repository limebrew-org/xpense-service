package in.limebrew.xpenseservice.service.impl;

import in.limebrew.xpenseservice.repository.TransactionRepository;
import in.limebrew.xpenseservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void getAllTransactions(String profileId){}

    @Override
    public void getTransactionsByQuery(String profileId,
                                       Date creationDate,
                                       String creationMonth,
                                       int creationYear,
                                       double transactionAmount,
                                       String transactionType,
                                       String transactionTag,
                                       String transactionRemarks){

    }

    @Override
    public void getTransactionsByRange(Date startDate, Date endDate, double startAmount, double endAmount){}
}
