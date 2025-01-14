package com.PayoutEngine.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "amount")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Amount {
    @EmbeddedId
    private AmountId amountId;

    @ManyToOne
    @MapsId("paymentid")
    @JoinColumn(name = "paymentid", referencedColumnName = "paymentid")
    private PaymentTransactionDetails paymentTransactionDetails;

    private String currencycode;
    private Double value;
    private Integer valuemultiplier;
    private Integer fxrate;
    private Integer fxratemultiplier;;
    private LocalDateTime modifiedon;

}
