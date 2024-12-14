package com.PayoutEngine.utilityServices;

import com.PayoutEngine.repository.dao.PayoutTimerScheduledRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SchedulerJob {

    @Autowired
    PayoutTimerScheduledRepository payoutTimerScheduledRepository;
    private static final Logger log = LoggerFactory.getLogger(SchedulerJob.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Transactional
    @Scheduled(fixedRate = 10000, initialDelay = 5000)
    public void reportCurrentTime() {
        payoutTimerScheduledRepository.changePayoutTimerStatusToProcessing();
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
