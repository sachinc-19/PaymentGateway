package com.PaymentEngine.repository.dao;

import com.PaymentEngine.repository.entities.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction,String> {
}
