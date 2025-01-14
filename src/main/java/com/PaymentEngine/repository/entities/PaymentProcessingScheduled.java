package com.PaymentEngine.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "paymentprocessingscheduled")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedStoredProcedureQueries({ @NamedStoredProcedureQuery(name = "paymentProcessingThrottle", procedureName = "PAYMENT_PROCESSING_THROTTLE") })
public class PaymentProcessingScheduled {
    @Id
    private String paymentid;
    private LocalDateTime creationtime;
    private LocalDateTime scheduletime;
    private String timerdata;
    private String status;
    private String partner;
    private String apiname;
}
