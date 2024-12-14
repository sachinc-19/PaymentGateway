package com.PayoutEngine.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayoutTxnDetails {
    private String payoutId;
    @NotBlank(message = "Please provide a correlationID")
    private String correlationId;
    private String mtcn;
    private String channel;
    @Valid
    private TransferDetails transferDetails;
    private String purpose;
    @Valid
    private PartnerDetails partnerDetails;
    @NotBlank(message = "Please provide a paymentMethod")
    private String paymentMethod;
}
