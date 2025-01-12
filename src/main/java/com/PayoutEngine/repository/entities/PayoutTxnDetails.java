package com.PayoutEngine.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "payouttxndetails")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PayoutTxnDetails {

    @Id
    private String payoutid;
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
