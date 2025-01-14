package com.PaymentEngine.engine;
import com.PaymentEngine.exceptions.customExceptions.ApplicationException;
import com.PaymentEngine.model.PaymentRequest;
import com.PaymentEngine.processor.PaymentServiceProvider;
import com.PaymentEngine.processor.PayoutProcessor;
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

    public void handlePayout(PaymentRequest paymentRequest) {
        try {
            // Step 1: Perform common field validation (account number, name, currency)
             validateCommonFields(paymentRequest);

            // Step 2: Route to the appropriate PSP based on country and rules
            PaymentServiceProvider selectedPsp = routingEngine.getBestPspForTransaction(paymentRequest);

            // Step 3: Forward the transaction to the processor for PSP-specific validation and processing
            payoutProcessor.processTransaction(paymentRequest, selectedPsp);
        }
        catch (ApplicationException ex) {
            System.out.println("Engine exception occured at line 34");
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateCommonFields(PaymentRequest paymentRequest) {
        // Perform common validation logic for international transfers
        if (paymentRequest.getPaymentDetails().getPspDetails().getAccountNumber() == null || paymentRequest.getPaymentDetails().getPspDetails().getAccountNumber().isEmpty()) {
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
