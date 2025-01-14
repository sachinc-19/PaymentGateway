package com.PayoutEngine.processor;
import com.PayoutEngine.exceptions.customExceptions.ApplicationException;
import com.PayoutEngine.model.ErrorHandler;
import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.processor.PSP1Processor.RuleFunctions.PartnerAPICalls;
import com.PayoutEngine.repository.dbOperation.UpdateDbObjects;
import com.PayoutEngine.utilityServices.CreatePayoutTimer;
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
    public void processTransaction(PayoutRequest payoutRequest, PaymentServiceProvider psp) {

        try {
            // Step 1: Call Partner specific certifyInput rule function to perform partner specific validations locally
            psp.certifyInput(payoutRequest);

            // Step 2: Call Partner specific certifyInputWithPartner rule function to perform validations with partner using remote web service
            psp.certifyInputWithPartner(payoutRequest);

            if(!errorHandler.getErrors().isEmpty()) {
                throw new ApplicationException("One or more validations failed", 400);
            }

            // step 3: Create the remit timer in db
            String paymentId = payoutRequest.getPaymentDetails().getPaymentId();

            createPayoutTimer.upsertTimerInDb(paymentId, LocalDateTime.now(), 1, "INCREMENT",
                    "NEW", payoutRequest.getPaymentDetails().getPspDetails().getPartnerName(), "REMIT");

            // Step 4: Update transaction status in the database (if applicable)
            updateTransactionStatus(payoutRequest);
        }
        catch (ApplicationException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean processExternalPayout(PayoutRequest payoutRequest) {
        // Simulate external payment processing logic
        System.out.println("Processing payout to receiver: " + payoutRequest.getPaymentDetails().getPspDetails().getBeneficiaryName() + ", having bank account number:" + payoutRequest.getPaymentDetails().getPspDetails().getAccountNumber());
        // For now, we'll assume it's always successful
        return true;
    }

    private void updateTransactionStatus(PayoutRequest payoutRequest) {
        // Simulate transaction status update (e.g., in a database)
        try {
            updateDbObjects.updatePayoutTxn(payoutRequest.getPaymentDetails().getPaymentId(), "PAYOUT", "QUEUED_FOR_PARTNER", "Unpaid", "Transaction is queued for processing");
            System.out.println("Processing payout to receiver: " + payoutRequest.getPaymentDetails().getPspDetails().getBeneficiaryName() + ", having bank account number:" + payoutRequest.getPaymentDetails().getPspDetails().getAccountNumber());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
