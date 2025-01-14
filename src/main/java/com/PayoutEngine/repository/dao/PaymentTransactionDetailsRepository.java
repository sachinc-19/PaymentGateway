package com.PayoutEngine.repository.dao;

import com.PayoutEngine.repository.entities.PaymentTransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionDetailsRepository extends JpaRepository<PaymentTransactionDetails, String> {
}
