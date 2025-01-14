package com.PayoutEngine.repository.dbOperation;

import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.repository.dao.PaymentTransactionRepository;
import com.PayoutEngine.repository.entities.PaymentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PayoutTransaction {

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepositoryDao;

    public void save(PayoutRequest payoutRequest) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setPaymentid("220");
        System.out.println("inside payoutxn save");
        // Set paymentTransaction db object with payoutTransaction data(code) object
        paymentTransactionRepositoryDao.save(paymentTransaction);
    }
}
