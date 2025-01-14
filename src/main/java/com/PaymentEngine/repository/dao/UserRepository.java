package com.PaymentEngine.repository.dao;

import com.PaymentEngine.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // Spring Data JPA will automatically implement this
}
