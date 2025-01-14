package com.PayoutEngine.repository.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class AmountId implements Serializable {

    private String paymentid;

    private String amounttype;

//    // equals() and hashCode()
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AMOUNTID amountId = (AMOUNTID) o;
//        return Objects.equals(PAYOUTID, amountId.PAYOUTID) &&
//                Objects.equals(AMOUNTTYPE, amountId.AMOUNTTYPE);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(PAYOUTID, AMOUNTTYPE);
//    }
}
