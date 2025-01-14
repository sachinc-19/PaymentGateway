package com.PaymentEngine.repository.dao;

import com.PaymentEngine.repository.entities.PaymentTransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionDetailsRepository extends JpaRepository<PaymentTransactionDetails, String> {
}
