package com.PaymentEngine.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Header header;
    @Valid
    private Credential credential;
    @NotBlank(message = "Please provide operation name")
    private String operation;
    @Valid
    private PaymentDetails paymentDetails;

}
