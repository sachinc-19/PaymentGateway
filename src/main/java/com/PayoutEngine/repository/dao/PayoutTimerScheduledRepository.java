package com.PayoutEngine.repository.dao;

import com.PayoutEngine.repository.entities.PayoutTimerScheduled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PayoutTimerScheduledRepository extends JpaRepository<PayoutTimerScheduled, String> {

    // change the timer status from NEW to PROCESSING based on partner throttling
    @Procedure("PRC_PAYOUTTIMER_THROTTLE")
    public void changePayoutTimerStatusToProcessing();

    public List<PayoutTimerScheduled> findByStatus(String status);

    public void deleteByStatus(String status);
}
