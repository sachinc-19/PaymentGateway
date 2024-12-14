package com.PayoutEngine.repository.dao;

import com.PayoutEngine.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // Spring Data JPA will automatically implement this
}
