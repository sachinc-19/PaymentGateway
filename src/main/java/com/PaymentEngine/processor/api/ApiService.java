package com.PaymentEngine.processor.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService {
    @Autowired
    private PSPApiClient pspApiClient;

    public void executeApiCalls() {
        // Example 1: GET Request
//        Map<String, String> urlParams = Map.of("id", "12345");
//        ResponseEntity<?> response = pspApiClient.callApi("getUserDetails", urlParams, null);
//        System.out.println(response.getBody());

        // Example 2: POST Request
//        Object requestBody = new TransactionRequest(...);
//        ResponseEntity<?> response2 = pspApiClient.callApi("postTransaction", Collections.emptyMap(), requestBody);
//        System.out.println(response2.getBody());
    }
}

