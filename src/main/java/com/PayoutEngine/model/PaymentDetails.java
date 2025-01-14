package com.PayoutEngine.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {
    private String paymentId;
    private String channel;
    @Valid
    private TransferDetails transferDetails;
    private String purpose;
    @Valid
    private PspDetails pspDetails;
    @NotBlank(message = "Please provide a paymentMethod")
    private String paymentMethod;
}
