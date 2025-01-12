package com.PayoutEngine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PSPRouting {
    String sendCountryCode;
    String receiveCountryCode;
    String receiveCurrency;
    String deliveryService;
    double receiveAmount;
    String partner;
}
