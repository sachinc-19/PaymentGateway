package com.PayoutEngine.engine;

import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.processor.PaymentServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoutingEngine {
    @Autowired
    private PSPFactory pspFactory;

    public RoutingEngine(PSPFactory pspFactory) {
        this.pspFactory = pspFactory;
    }

    /**
     * Routes the transaction to the best available PSP based on rules.
     *
     * @param payoutRequest The incoming transaction request.
     * @return The best PSP based on routing rules.
     * @throws RoutingException If no suitable PSP is found.
     */
    public PaymentServiceProvider getBestPspForTransaction(PayoutRequest payoutRequest) throws RoutingException {
//        String receivingCountry = payoutRequest.getReceiverCountry();
//        double amount = payoutRequest.getAmount();
//        String currency = payoutRequest.getCurrency();
//
//        // Fetch PSPs available for this country
//        List<PSPInfo> availablePSPs = pspFactory.getAvailablePSPs(receivingCountry);
//
//        // Apply business rules to select the optimal PSP
//        Optional<PSPInfo> selectedPSP = availablePSPs.stream()
//                .filter(psp -> psp.supportsCurrency(currency))       // Currency support check
//                .filter(psp -> psp.supportsAmount(amount))           // Amount range check
//                .sorted((psp1, psp2) -> comparePSPs(psp1, psp2))     // Sort by business rules (fees, speed, etc.)
//                .findFirst();
//
//        if (!selectedPSP.isPresent()) {
//            throw new RoutingException("No suitable PSP found for country: " + receivingCountry);
//        }
        return pspFactory.getPSP("DIGIT9");
    }

    /**
     * Compares two PSPs based on business rules. The comparison logic can be customized as per the requirements.
     *
     * @param psp1 First PSP
     * @param psp2 Second PSP
     * @return A negative value if psp1 is preferred over psp2, a positive value if psp2 is preferred over psp1, 0 if equal
     */
//    private int comparePSPs(PSPInfo psp1, PSPInfo psp2) {
//        // Example rule: prefer PSP with lower fees
//        int feeComparison = Double.compare(psp1.getTransactionFee(), psp2.getTransactionFee());
//        if (feeComparison != 0) {
//            return feeComparison;
//        }
//
//        // Example rule: if fees are equal, prefer faster PSP
//        return Long.compare(psp1.getProcessingTime(), psp2.getProcessingTime());
//    }

    public class RoutingException extends RuntimeException {
        public RoutingException(String message) {
            super(message);
        }
    }
}
