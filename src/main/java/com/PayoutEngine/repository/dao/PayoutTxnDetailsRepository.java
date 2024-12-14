package com.PayoutEngine.repository.dao;

import com.PayoutEngine.repository.entities.PayoutTxnDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayoutTxnDetailsRepository extends JpaRepository<PayoutTxnDetails, String> {
}
