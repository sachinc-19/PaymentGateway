package com.PayoutEngine.processor.PSP2Processor;

import com.PayoutEngine.model.ErrorHandler;
import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.processor.PaymentServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("PSP2")
public class PSP2 implements PaymentServiceProvider {
    @Autowired
    ErrorHandler errorHandler = new ErrorHandler();
    @Override
    public boolean certifyInput(PayoutRequest payoutRequest) {
        System.out.println("calling certifyInput from PSP2");

        // Example validations
        if (payoutRequest.getPaymentDetails().getPspDetails().getAccountNumber() == null || payoutRequest.getPaymentDetails().getPspDetails().getAccountNumber().isEmpty()) {
            errorHandler.addError("accountNumber", "Account number cannot be empty");
        }

        if (payoutRequest.getPaymentDetails().getPspDetails().getBankCode() == null || payoutRequest.getPaymentDetails().getPspDetails().getBankCode().isEmpty()) {
            errorHandler.addError("bankCode", "Bank code is required");
        }

        if (payoutRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getValue() <= 0) {
            errorHandler.addError("amount", "Amount must be greater than zero");
        }

        // Add more field validations as needed
        // ...
        return true;
    }

    @Override
    public boolean certifyInputWithPartner(PayoutRequest payoutRequest) {
        System.out.println("calling certifyInputWithPartner from PSP2");
        return true;
    }

    @Override
    public void retryPartnerApi(String paymentId, String apiToInvoke) {
        System.out.println("retrying " + apiToInvoke + " API for PSP2 partner");
    }
}
