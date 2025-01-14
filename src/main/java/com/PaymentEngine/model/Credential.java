package com.PaymentEngine.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credential {
    @NotBlank(message = "Please provide a username")
    private String username;
    @NotBlank(message = "Please provide a password")
    private String password;
}
