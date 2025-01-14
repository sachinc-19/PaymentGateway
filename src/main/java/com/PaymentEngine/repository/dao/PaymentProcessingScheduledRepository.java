package com.PaymentEngine.repository.dao;

import com.PaymentEngine.repository.entities.PaymentProcessingScheduled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentProcessingScheduledRepository extends JpaRepository<PaymentProcessingScheduled, String> {

    // change the timer status from NEW to PROCESSING based on partner throttling
    @Procedure("PAYMENT_PROCESSING_THROTTLE")
    public void changePayoutTimerStatusToProcessing();

    public List<PaymentProcessingScheduled> findByStatus(String status);

    public void deleteByStatus(String status);
}
