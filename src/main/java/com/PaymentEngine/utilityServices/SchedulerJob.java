package com.PaymentEngine.utilityServices;

import com.PaymentEngine.engine.PSPFactory;
import com.PaymentEngine.processor.PaymentServiceProvider;
import com.PaymentEngine.repository.dao.PaymentProcessingScheduledRepository;
import com.PaymentEngine.repository.entities.PaymentProcessingScheduled;
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
    PaymentProcessingScheduledRepository paymentProcessingScheduledRepository;
    private static final Logger log = LoggerFactory.getLogger(SchedulerJob.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Transactional
    @Scheduled(fixedRate = 60000, initialDelay = 1000)
    public void PayoutTimerRun() throws JSONException {
        paymentProcessingScheduledRepository.changePayoutTimerStatusToProcessing();
        log.info("The time is now {}", dateFormat.format(new Date()));

        List<PaymentProcessingScheduled> timers = paymentProcessingScheduledRepository.findByStatus("PROCESSING");
        paymentProcessingScheduledRepository.deleteByStatus("PROCESSING");

        for(PaymentProcessingScheduled t : timers) {
            String paymentId = t.getPaymentid();
            String partnerName = t.getPartner();
            String apiName = t.getApiname();
            System.out.println("paymentId: " + paymentId + ", partnerName: " + partnerName + ", apiName: " + apiName);

            PaymentServiceProvider selectedPsp = pspFactory.getPSP(partnerName);
            System.out.println("Picking up the paymentId [" + paymentId + "] for processing");
            selectedPsp.retryPartnerApi(paymentId, apiName);
        }
    }
}
