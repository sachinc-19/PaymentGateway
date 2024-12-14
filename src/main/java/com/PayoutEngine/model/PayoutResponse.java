package com.PayoutEngine.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
public class PayoutResponse {
    private String status;
    private Integer code;
    private String message;

    // Only include `validationErrors` if it's not null or empty
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldError> validationErrors; // Optional validation errors

    public PayoutResponse(String status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
