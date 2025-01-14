package com.PaymentEngine.repository.dao;

import com.PaymentEngine.repository.entities.Amount;
import com.PaymentEngine.repository.entities.AmountId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmountRepository extends JpaRepository<Amount, AmountId> {
}
