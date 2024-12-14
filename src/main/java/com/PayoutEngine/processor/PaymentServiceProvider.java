package com.PayoutEngine.processor;

import com.PayoutEngine.model.PayoutRequest;

public interface PaymentServiceProvider {
    public boolean certifyInput(PayoutRequest payoutRequest);
    public boolean certifyInputWithPartner(PayoutRequest payoutRequest);
}
