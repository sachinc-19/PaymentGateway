package com.PayoutEngine.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
