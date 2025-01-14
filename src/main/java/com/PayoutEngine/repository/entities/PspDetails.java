package com.PayoutEngine.repository.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pspdetails")
@Getter
@Setter
public class PspDetails {

    @Id
    private String paymentid;
    private String accountnumber;
    private String partnerid;
    private String partnername;
    private String bankname;
    private String bankcode;
    private String branchcode;
    private String beneficiaryname;
    private LocalDateTime modifiedon;

    public PspDetails() {
    }
}
