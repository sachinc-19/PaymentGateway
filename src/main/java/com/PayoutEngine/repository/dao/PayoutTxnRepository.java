package com.PayoutEngine.repository.dao;

import com.PayoutEngine.repository.entities.PayoutTxn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayoutTxnRepository extends JpaRepository<PayoutTxn,String> {
}
