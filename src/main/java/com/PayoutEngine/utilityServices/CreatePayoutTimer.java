package com.PayoutEngine.utilityServices;

import com.PayoutEngine.repository.dao.PayoutTimerScheduledRepository;
import com.PayoutEngine.repository.entities.PayoutTimerScheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class CreatePayoutTimer {
    @Autowired
    PayoutTimerScheduledRepository payoutTimerScheduledRepository;

    public void upsertTimerInDb(String timerName, LocalDateTime creationTime, Integer intervalInMins,
                                    String timerData, String status, String partner, String apiName) {

        PayoutTimerScheduled payoutTimerScheduled = new PayoutTimerScheduled();
        payoutTimerScheduled.setTimername(timerName);
        payoutTimerScheduled.setCreationtime(creationTime);

        LocalDateTime scheduleTime = creationTime.plusMinutes(intervalInMins);
        payoutTimerScheduled.setScheduletime(scheduleTime);

        payoutTimerScheduled.setTimerdata(timerData);
        payoutTimerScheduled.setStatus(status);
        payoutTimerScheduled.setPartner(partner);
        payoutTimerScheduled.setApiname(apiName);

        System.out.println("Creating payout timer in DB");

        payoutTimerScheduledRepository.save(payoutTimerScheduled);
    }
}
