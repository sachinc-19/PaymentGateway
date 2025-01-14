package com.PaymentEngine.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Amount {
    @NotBlank(message = "Please provide a currencyCode")
    @Pattern(regexp = "^[a-zA-Z]{3}+$", message = "currencyCode must contain only alphabets")
    private String currencyCode;

    @Min(value = 1, message = "Amount must be greater than 0")
    private double value;
}
