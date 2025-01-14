package com.PaymentEngine.processor;
import com.PaymentEngine.exceptions.customExceptions.ApplicationException;
import com.PaymentEngine.model.ErrorHandler;
import com.PaymentEngine.model.PaymentRequest;
import com.PaymentEngine.processor.PSP1Processor.RuleFunctions.PartnerAPICalls;
import com.PaymentEngine.repository.dbOperation.UpdateDbObjects;
import com.PaymentEngine.utilityServices.CreatePayoutTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PayoutProcessor {
    @Autowired
    private PartnerAPICalls partnerAPICalls;
    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private CreatePayoutTimer createPayoutTimer;
    @Autowired
    private UpdateDbObjects updateDbObjects;

    // Step 1: Simulate interaction with external payment system (e.g., bank or card processor)
    public void processTransaction(PaymentRequest paymentRequest, PaymentServiceProvider psp) {

        try {
            // Step 1: Call Partner specific certifyInput rule function to perform partner specific validations locally
            psp.certifyInput(paymentRequest);

            // Step 2: Call Partner specific certifyInputWithPartner rule function to perform validations with partner using remote web service
            psp.certifyInputWithPartner(paymentRequest);

            if(!errorHandler.getErrors().isEmpty()) {
                throw new ApplicationException("One or more validations failed", 400);
            }

            // step 3: Create the remit timer in db
            String paymentId = paymentRequest.getPaymentDetails().getPaymentId();

            createPayoutTimer.upsertTimerInDb(paymentId, LocalDateTime.now(), 1, "INCREMENT",
                    "NEW", paymentRequest.getPaymentDetails().getPspDetails().getPartnerName(), "REMIT");

            // Step 4: Update transaction status in the database (if applicable)
            updateTransactionStatus(paymentRequest);
        }
        catch (ApplicationException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean processExternalPayout(PaymentRequest paymentRequest) {
        // Simulate external payment processing logic
        System.out.println("Processing payout to receiver: " + paymentRequest.getPaymentDetails().getPspDetails().getBeneficiaryName() + ", having bank account number:" + paymentRequest.getPaymentDetails().getPspDetails().getAccountNumber());
        // For now, we'll assume it's always successful
        return true;
    }

    private void updateTransactionStatus(PaymentRequest paymentRequest) {
        // Simulate transaction status update (e.g., in a database)
        try {
            updateDbObjects.updatePayoutTxn(paymentRequest.getPaymentDetails().getPaymentId(), "PAYOUT", "QUEUED_FOR_PARTNER", "Unpaid", "Transaction is queued for processing");
            System.out.println("Processing payout to receiver: " + paymentRequest.getPaymentDetails().getPspDetails().getBeneficiaryName() + ", having bank account number:" + paymentRequest.getPaymentDetails().getPspDetails().getAccountNumber());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
