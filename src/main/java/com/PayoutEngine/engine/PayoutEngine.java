package com.PayoutEngine.engine;
import com.PayoutEngine.exceptions.customExceptions.ApplicationException;
import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.processor.PaymentServiceProvider;
import com.PayoutEngine.processor.PayoutProcessor;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PayoutEngine {

    @Autowired
    private PayoutProcessor payoutProcessor;
    @Autowired
    private RoutingEngine routingEngine;

    public void handlePayout(PayoutRequest payoutRequest) {
        try {
            // Step 1: Perform common field validation (account number, name, currency)
             validateCommonFields(payoutRequest);

            // Step 2: Route to the appropriate PSP based on country and rules
            PaymentServiceProvider selectedPsp = routingEngine.getBestPspForTransaction(payoutRequest);

            // Step 3: Forward the transaction to the processor for PSP-specific validation and processing
            payoutProcessor.processTransaction(payoutRequest, selectedPsp);
        }
        catch (ApplicationException ex) {
            System.out.println("Engine exception occured at line 34");
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateCommonFields(PayoutRequest payoutRequest) {
        // Perform common validation logic for international transfers
        if (payoutRequest.getPaymentDetails().getPspDetails().getAccountNumber() == null || payoutRequest.getPaymentDetails().getPspDetails().getAccountNumber().isEmpty()) {
            throw new ValidationException("Invalid account number");
        }
        // Other field validations
    }

    private BigDecimal convertCurrency(BigDecimal amount, String currency) {
        // Assume we handle currency conversion logic here
        // In a real-world scenario, you would call a Forex API to convert the currency
        if (!"USD".equals(currency)) {
            return amount.multiply(new BigDecimal("1.2"));  // Simulating conversion to USD
        }
        return amount;
    }
}
