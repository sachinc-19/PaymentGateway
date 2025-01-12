package com.PayoutEngine.repository.dbOperation;

import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.repository.dao.PayoutTxnRepository;
import com.PayoutEngine.repository.entities.PayoutTxn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PayoutTransaction {

    @Autowired
    private PayoutTxnRepository payoutTxnRepositoryDao;

    public void save(PayoutRequest payoutRequest) {
        PayoutTxn payoutTxn = new PayoutTxn();
        payoutTxn.setPayoutid("220");
        System.out.println("inside payoutxn save");
        // Set payoutTxn db object with payoutTransaction data(code) object
        payoutTxnRepositoryDao.save(payoutTxn);
    }
}
