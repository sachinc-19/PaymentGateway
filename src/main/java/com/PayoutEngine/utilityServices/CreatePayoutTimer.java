package com.PayoutEngine.utilityServices;

import com.PayoutEngine.repository.dao.PaymentProcessingScheduledRepository;
import com.PayoutEngine.repository.entities.PaymentProcessingScheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class CreatePayoutTimer {
    @Autowired
    PaymentProcessingScheduledRepository paymentProcessingScheduledRepository;

    public void upsertTimerInDb(String paymentId, LocalDateTime creationTime, Integer intervalInMins,
                                    String timerData, String status, String partner, String apiName) {

        PaymentProcessingScheduled paymentProcessingScheduled = new PaymentProcessingScheduled();
        paymentProcessingScheduled.setPaymentid(paymentId);
        paymentProcessingScheduled.setCreationtime(creationTime);

        LocalDateTime scheduleTime = creationTime.plusMinutes(intervalInMins);
        paymentProcessingScheduled.setScheduletime(scheduleTime);

        paymentProcessingScheduled.setTimerdata(timerData);
        paymentProcessingScheduled.setStatus(status);
        paymentProcessingScheduled.setPartner(partner);
        paymentProcessingScheduled.setApiname(apiName);

        System.out.println("Scheduling [" + apiName + "] timer for [" + partner + "] partner at time [" + scheduleTime + "]");

        paymentProcessingScheduledRepository.save(paymentProcessingScheduled);
    }
}
