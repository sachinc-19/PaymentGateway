package com.PayoutEngine.repository.dao;

import com.PayoutEngine.repository.entities.Amount;
import com.PayoutEngine.repository.entities.AmountId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmountRepository extends JpaRepository<Amount, AmountId> {
}
