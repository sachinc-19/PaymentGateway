package com.PayoutEngine.repository.dao;

import com.PayoutEngine.repository.entities.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction,String> {
}
