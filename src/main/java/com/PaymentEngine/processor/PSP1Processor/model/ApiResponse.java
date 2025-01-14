package com.PaymentEngine.processor.PSP1Processor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String refID;
    private String orderID;
    private String statusCode;
    private String statusSubCode;
    private String statusDesc;
}
