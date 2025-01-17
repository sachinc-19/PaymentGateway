package com.PaymentEngine.processor.PSP1Processor.PspHandler;

import com.PaymentEngine.processor.PSP1Processor.model.ApiResponse;
import com.PaymentEngine.repository.dbOperation.UpdateDbObjects;
import com.PaymentEngine.utilityServices.CreatePayoutTimer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ResponseHandling {
    private Map<String, Object> apiRetryConfig;
    private Map<String, Object> stateChangeConfig;
    @Autowired
    private UpdateDbObjects updateDbObjects;
    @Autowired
    private CreatePayoutTimer createPayoutTimer;

    public ResponseHandling() {
        System.out.println("hello");
        // Load decision tables from JSON/YAML files
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("src/main/java/com/PaymentEngine/processor/PSP1Processor/DecisionRules/APIResponseCodes.json");
            // Convert JSON string to Map
            Map<String, Object> config = mapper.readValue(file, Map.class);
            this.apiRetryConfig = config;
            file = new File("src/main/java/com/PaymentEngine/processor/PSP1Processor/DecisionRules/TransitionState.json");
            config = mapper.readValue(file, Map.class);
            this.stateChangeConfig = config;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionOnValidateResponse() {
    }

    public void actionOnServiceResponse(String paymentId, String apiName, ApiResponse apiResponse) throws JSONException {
        System.out.println("-----------------Inside ResponseHandling actionOnServiceResponse PSP1-----------------");
        //        JSONObject responseObj = new JSONObject(String.valueOf(response));
        String responseCode = apiResponse.getStatusCode();
        String subResponseCode = apiResponse.getStatusSubCode();
        System.out.println("Moving transaction status based on " + apiName + " API response");
        // 1. change the transaction state if required based on api response
        System.out.println("api: " + apiName + ", responseCode: " + responseCode + ", subResponseCode: " + subResponseCode);
        // Get the rules array
        List<Map<String, Object>> rules = (List<Map<String, Object>>) stateChangeConfig.get("rules");

        Map<String, Object> actions = null;
        // Loop through each rule and access its properties
        for (Map<String, Object> rule : rules) {
            if (rule.get("service").equals(apiName) &&
                    rule.get("partnerStatus").equals(responseCode) &&
                    rule.get("partnerSubStatus").equals(subResponseCode)) {
                // Access the actions object within each rule
                actions = (Map<String, Object>) rule.get("actions");
                System.out.println("transitionName: " + actions.get("transitionName"));
                System.out.println("partnerStatusDescription: " + actions.get("partnerStatusDescription"));
                System.out.println("----");
            }
        }
        String transitionName = (String) actions.get("transitionName");
        String description = (String) actions.get("partnerStatusDescription");
        System.out.println("transitionName: " + transitionName + ", description: " + description);
        updateDbObjects.updatePayoutTxn(paymentId, "PAYOUT", transitionName, responseCode+" | "+subResponseCode, description);
        checkForRetry(paymentId, apiName, responseCode, subResponseCode);
    }

    public void checkForRetry(String paymentId, String apiName, String responseCode , String subResponseCode) {
        System.out.println("api: " + apiName + ", responseCode: " + responseCode + ", subResponseCode: " + subResponseCode);
        // Get the rules array
        List<Map<String, Object>> rules = (List<Map<String, Object>>) apiRetryConfig.get("rules");

        Map<String, Object> actions = null;
        // Loop through each rule and access its properties
        for (Map<String, Object> rule : rules) {
            if (rule.get("api").equals(apiName) &&
                    rule.get("responseCode").equals(responseCode) &&
                    rule.get("subResponseCode").equals(subResponseCode)) {
                // Access the actions object within each rule
                actions = (Map<String, Object>) rule.get("actions");
                System.out.println("Is Success Code: " + actions.get("isSuccessCode"));
                System.out.println("Raise Alert: " + actions.get("raiseAlert"));
                System.out.println("Response Message: " + actions.get("responseMsg"));
                System.out.println("Retry Required: " + actions.get("retryRequired"));
                System.out.println("Retry Interval (min): " + actions.get("retryIntervalInMin"));
                System.out.println("API to Call: " + actions.get("apiToCall"));
                System.out.println("----");
            }
        }
        boolean retryRequired = (boolean) actions.get("retryRequired");

        if(retryRequired) {
            Integer retryIntervalInMins = (Integer) actions.get("retryIntervalInMin");
            String apiToCall = (String) actions.get("apiToCall");

            createPayoutTimer.upsertTimerInDb(paymentId, LocalDateTime.now(), retryIntervalInMins, "INCREMENT",
                    "NEW", "PSP1", apiToCall);
        }
    }
}
