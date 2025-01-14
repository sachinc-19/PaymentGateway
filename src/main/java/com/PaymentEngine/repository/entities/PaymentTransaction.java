package com.PaymentEngine.repository.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "paymenttransaction")
@Getter
@Setter
@NoArgsConstructor
public class PaymentTransaction {

    @Id
    private String paymentid;
    private String channel;
    private String state;
    private String substate;
    private String status;
    private String description;
    private String reasoncode;
    private LocalDateTime lastupdated;
    private LocalDateTime modifiedon;
    private LocalDateTime createdon;
}
