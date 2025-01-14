package com.PaymentEngine.repository.dbOperation;

import com.PaymentEngine.model.PaymentRequest;
import com.PaymentEngine.repository.dao.PaymentTransactionRepository;
import com.PaymentEngine.repository.entities.PaymentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PayoutTransaction {

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepositoryDao;

    public void save(PaymentRequest paymentRequest) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setPaymentid("220");
        System.out.println("inside payoutxn save");
        // Set paymentTransaction db object with payoutTransaction data(code) object
        paymentTransactionRepositoryDao.save(paymentTransaction);
    }
}
