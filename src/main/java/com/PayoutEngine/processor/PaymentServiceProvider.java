package com.PayoutEngine.processor;

import com.PayoutEngine.model.PayoutRequest;
import org.json.JSONException;

public interface PaymentServiceProvider {
    public boolean certifyInput(PayoutRequest payoutRequest);
    public boolean certifyInputWithPartner(PayoutRequest payoutRequest);
    public void retryPartnerApi(String payoutId, String apiToInvoke) throws JSONException;
}
