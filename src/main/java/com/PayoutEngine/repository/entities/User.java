package com.PayoutEngine.repository.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String username;
    private String password; // store the hashed password
}
