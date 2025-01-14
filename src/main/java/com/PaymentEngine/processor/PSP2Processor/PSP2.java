package com.PaymentEngine.processor.PSP2Processor;

import com.PaymentEngine.model.ErrorHandler;
import com.PaymentEngine.model.PaymentRequest;
import com.PaymentEngine.processor.PaymentServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("PSP2")
public class PSP2 implements PaymentServiceProvider {
    @Autowired
    ErrorHandler errorHandler = new ErrorHandler();
    @Override
    public boolean certifyInput(PaymentRequest paymentRequest) {
        System.out.println("calling certifyInput from PSP2");

        // Example validations
        if (paymentRequest.getPaymentDetails().getPspDetails().getAccountNumber() == null || paymentRequest.getPaymentDetails().getPspDetails().getAccountNumber().isEmpty()) {
            errorHandler.addError("accountNumber", "Account number cannot be empty");
        }

        if (paymentRequest.getPaymentDetails().getPspDetails().getBankCode() == null || paymentRequest.getPaymentDetails().getPspDetails().getBankCode().isEmpty()) {
            errorHandler.addError("bankCode", "Bank code is required");
        }

        if (paymentRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getValue() <= 0) {
            errorHandler.addError("amount", "Amount must be greater than zero");
        }

        // Add more field validations as needed
        // ...
        return true;
    }

    @Override
    public boolean certifyInputWithPartner(PaymentRequest paymentRequest) {
        System.out.println("calling certifyInputWithPartner from PSP2");
        return true;
    }

    @Override
    public void retryPartnerApi(String paymentId, String apiToInvoke) {
        System.out.println("retrying " + apiToInvoke + " API for PSP2 partner");
    }
}
