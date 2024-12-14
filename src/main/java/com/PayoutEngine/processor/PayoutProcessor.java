package com.PayoutEngine.processor;
import com.PayoutEngine.exceptions.customExceptions.ApplicationException;
import com.PayoutEngine.exceptions.customExceptions.ProcessorException;
import com.PayoutEngine.model.ErrorHandler;
import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.processor.PSP1Processor.RuleFunctions.PartnerAPICalls;
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

    // Step 1: Simulate interaction with external payment system (e.g., bank or card processor)
    public void processTransaction(PayoutRequest payoutRequest, PaymentServiceProvider psp) {

        try {
            // Step 1: Call Partner specific certifyInput rule function to perform partner specific validations locally
            psp.certifyInput(payoutRequest);

            // Step 2: Call Partner specific certifyInputWithPartner rule function to perform validations with partner using remote web service
            psp.certifyInputWithPartner(payoutRequest);

            // Step 3: Update transaction status in the database (if applicable)
            updateTransactionStatus(payoutRequest);

            if(!errorHandler.getErrors().isEmpty()) {
                throw new ApplicationException("One or more validations failed", 400);
            }

            // step 4: Create the remit timer in db
            String timerName = payoutRequest.getPayoutTxnDetails().getPayoutId();

            createPayoutTimer.upsertTimerInDb(timerName, LocalDateTime.now(), 5, "INCREMENT",
                    "NEW", payoutRequest.getPayoutTxnDetails().getPartnerDetails().getPartnerName(), "REMIT");
        }
        catch (ApplicationException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean processExternalPayout(PayoutRequest payoutRequest) {
        // Simulate external payment processing logic
        System.out.println("Processing payout to receiver: " + payoutRequest.getPayoutTxnDetails().getPartnerDetails().getBeneficiaryName() + ", having bank account number:" + payoutRequest.getPayoutTxnDetails().getPartnerDetails().getAccountNumber());
        // For now, we'll assume it's always successful
        return true;
    }

    private void updateTransactionStatus(PayoutRequest payoutRequest) {
        // Simulate transaction status update (e.g., in a database)
        System.out.println("Transaction for sender completed." + payoutRequest.getPayoutTxnDetails().getPartnerDetails().getBeneficiaryName() + ", having bank account number:" + payoutRequest.getPayoutTxnDetails().getPartnerDetails().getAccountNumber());
    }

}
