package com.PayoutEngine.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferDetails {

    @NotBlank(message = "Please provide a sendCountryCode")
    @Pattern(regexp = "^[a-zA-Z]{2}+$", message = "sendCountryCode must be a 2 letter ISO CODE")
    private String sendCountryCode;

    @Valid
    private Amount sendAmount;

    @NotBlank(message = "Please provide a receiveCountryCode")
    @Pattern(regexp = "^[a-zA-Z]{2}+$", message = "receiveCountryCode must be a 2 letter ISO CODE")
    private String receiveCountryCode;

    @Valid
    private Amount receiveAmount;
}
