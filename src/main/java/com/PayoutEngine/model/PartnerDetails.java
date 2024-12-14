package com.PayoutEngine.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerDetails {

    @NotBlank(message = "Please provide a accountNumber")
    private String accountNumber;

    private String partnerName;

    @NotBlank(message = "Please provide a bankName")
    @Pattern(regexp = "^[a-zA-Z\\s.]+$", message = "bankName must be only of alphabets and spaces")
    private String bankName;

    @NotBlank(message = "Please provide a bankCode")
    private String bankCode;

    private String branchCode;

    @NotBlank(message = "Please provide a beneficiaryName")
    @Pattern(regexp = "^[a-zA-Z\\s.]{2,50}$", message = "beneficiaryName must of 2 to 50 length with only alphabets and spaces")
    private String beneficiaryName;
}
