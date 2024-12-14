package com.PayoutEngine.processor.PSP1Processor;

import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.processor.PSP1Processor.RuleFunctions.PartnerAPICalls;
import com.PayoutEngine.processor.PaymentServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("VISAINTL")
public class PSP1 implements PaymentServiceProvider {
    @Autowired
    PartnerAPICalls partnerAPICalls;

    @Override
    public boolean certifyInput(PayoutRequest payoutRequest) {
        System.out.println("calling certifyInput from VISAINTL");
        return true;
    }

    @Override
    public boolean certifyInputWithPartner(PayoutRequest payoutRequest) {
        System.out.println("calling certifyInputWithPartner from VISAINTL");
        partnerAPICalls.validatePaymentAPI(payoutRequest);
        return true;
    }
}
