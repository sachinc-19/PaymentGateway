package com.PaymentEngine.repository.dao;

import com.PaymentEngine.repository.entities.PspDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PspDetailsRepository extends JpaRepository<PspDetails,String> {
}
