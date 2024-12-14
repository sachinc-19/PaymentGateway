package com.PayoutEngine.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "payouttimerscheduled")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedStoredProcedureQueries({ @NamedStoredProcedureQuery(name = "payoutTimersThrottle", procedureName = "PRC_PAYOUTTIMER_THROTTLE") })
public class PayoutTimerScheduled {
    @Id
    private String timername;
    private LocalDateTime creationtime;
    private LocalDateTime scheduletime;
    private String timerdata;
    private String status;
    private String partner;
    private String apiname;
}
