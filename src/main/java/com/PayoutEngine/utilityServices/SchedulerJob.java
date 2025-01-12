package com.PayoutEngine.utilityServices;

import com.PayoutEngine.engine.PSPFactory;
import com.PayoutEngine.processor.PaymentServiceProvider;
import com.PayoutEngine.repository.dao.PayoutTimerScheduledRepository;
import com.PayoutEngine.repository.entities.PayoutTimerScheduled;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class SchedulerJob {

    @Autowired
    private PSPFactory pspFactory;
    @Autowired
    PayoutTimerScheduledRepository payoutTimerScheduledRepository;
    private static final Logger log = LoggerFactory.getLogger(SchedulerJob.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Transactional
    @Scheduled(fixedRate = 60000, initialDelay = 1000)
    public void PayoutTimerRun() throws JSONException {
        payoutTimerScheduledRepository.changePayoutTimerStatusToProcessing();
        log.info("The time is now {}", dateFormat.format(new Date()));

        List<PayoutTimerScheduled> timers = payoutTimerScheduledRepository.findByStatus("PROCESSING");
        payoutTimerScheduledRepository.deleteByStatus("PROCESSING");

        for(PayoutTimerScheduled t : timers) {
            String timerName = t.getTimername();
            String partnerName = t.getPartner();
            String apiName = t.getApiname();
            System.out.println("timerName: " + timerName + ", partnerName: " + partnerName + ", apiName: " + apiName);

            PaymentServiceProvider selectedPsp = pspFactory.getPSP(partnerName);
            System.out.println("Picking up the timerName [" + timerName + "] for processing");
            selectedPsp.retryPartnerApi(timerName, apiName);
        }
    }
}
