package com.PaymentEngine.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "paymenttransactiondetails")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentTransactionDetails {

    @Id
    private String paymentid;
    private String channel;
    private String partnername;
    private String partnerreference;
    private String sendcountrycode;
    private String receivecountrycode;
    private Integer overallfx;
    private Integer overallfxmultiplier;
    private String purpose;
    private LocalDateTime deliveryestimate;
    private LocalDateTime localdeliveryestimate;
    private LocalDateTime modifiedon;
    private LocalDateTime createdon;

//    @OneToMany(mappedBy = "payoutTxnDetails", cascade = CascadeType.ALL)
//    private Set<Amount> Amounts;
}
