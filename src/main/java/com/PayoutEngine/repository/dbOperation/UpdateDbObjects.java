package com.PayoutEngine.repository.dbOperation;

import com.PayoutEngine.repository.dao.PayoutTxnRepository;
import com.PayoutEngine.repository.entities.PayoutTxn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UpdateDbObjects {
    @Autowired
    private PayoutTxnRepository payoutTxnRepository;

    public void updatePayoutTxn(String payoutId, String state, String subState, String status, String description) {
//        Optional<PayoutTxn> payoutTxn = payoutTxnRepository.findById(payoutId);
        PayoutTxn payoutTxn = payoutTxnRepository.findById(payoutId).orElse(null);
        System.out.println("updating payout Transaction Table");
        if(payoutTxn != null) {
            payoutTxn.setState(state);
            payoutTxn.setSubstate(subState);
            payoutTxn.setStatus(status);
            payoutTxn.setDescription(description);
            payoutTxnRepository.save(payoutTxn);
            System.out.println("updating payout Transaction Table");
        }
    }
}
