package com.PayoutEngine.processor.PSP1Processor.RuleFunctions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ResponseHandling {
    private Map<String, Object> responseConfig;

    public ResponseHandling() {
        System.out.println("hello");
        // Load decision tables from JSON/YAML files
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("src/main/java/com/PayoutEngine/processor/PSP1Processor/DecisionTables/APIResponseCodes.json");
            // Convert JSON string to Map
            Map<String, Object> config = mapper.readValue(file, Map.class);
            this.responseConfig = config;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionOnValidateResponse() {
    }

    public void actionOnServiceResponse() {

    }

    public void checkForRetry(String api, String responseCode , String subResponseCode) {
        // Get the rules array
        List<Map<String, Object>> rules = (List<Map<String, Object>>) responseConfig.get("rules");

        // Loop through each rule and access its properties
        for (Map<String, Object> rule : rules) {
            if (rule.get("api").equals(api) &&
                    rule.get("responseCode").equals(responseCode) &&
                    rule.get("subResponseCode").equals(subResponseCode)) {
                // Access the actions object within each rule
                Map<String, Object> actions = (Map<String, Object>) rule.get("actions");
                System.out.println("Is Success Code: " + actions.get("isSuccessCode"));
                System.out.println("Raise Alert: " + actions.get("raiseAlert"));
                System.out.println("Response Message: " + actions.get("responseMsg"));
                System.out.println("Retry Required: " + actions.get("retryRequired"));
                System.out.println("Retry Interval (min): " + actions.get("retryIntervalInMin"));
                System.out.println("API to Call: " + actions.get("apiToCall"));
                System.out.println("----");
            }
        }
    }
}
