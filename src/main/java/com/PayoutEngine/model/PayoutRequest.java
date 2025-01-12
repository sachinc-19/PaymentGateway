package com.PayoutEngine.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PayoutRequest {
    private Header header;
    @Valid
    private Credential credential;
    @NotBlank(message = "Please provide operation name")
    private String operation;
    @Valid
    private PayoutTxnDetails payoutTxnDetails;

}
