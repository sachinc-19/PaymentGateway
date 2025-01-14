package com.PayoutEngine.repository.dbOperation;

import com.PayoutEngine.repository.dao.PaymentTransactionRepository;
import com.PayoutEngine.repository.entities.PaymentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UpdateDbObjects {
    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    public void updatePayoutTxn(String paymentId, String state, String subState, String status, String description) {
//        Optional<PaymentTransaction> paymentTransaction = paymentTransactionRepository.findById(paymentId);
        PaymentTransaction paymentTransaction = paymentTransactionRepository.findById(paymentId).orElse(null);
        System.out.println("updating payout Transaction Table");
        if(paymentTransaction != null) {
            paymentTransaction.setState(state);
            paymentTransaction.setSubstate(subState);
            paymentTransaction.setStatus(status);
            paymentTransaction.setDescription(description);
            paymentTransactionRepository.save(paymentTransaction);
            System.out.println("updating payout Transaction Table");
        }
    }
}
