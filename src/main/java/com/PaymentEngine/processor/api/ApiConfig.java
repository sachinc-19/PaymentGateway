package com.PaymentEngine.processor.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiConfig {
    private String name;
    private String url;
    private String method;
//    private List<String> urlParameters;
    private boolean requestBodyRequired;

    // Getters and Setters
}
