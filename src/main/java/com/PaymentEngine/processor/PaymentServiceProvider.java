package com.PaymentEngine.processor;

import com.PaymentEngine.model.PaymentRequest;
import org.json.JSONException;

public interface PaymentServiceProvider {
    public boolean certifyInput(PaymentRequest paymentRequest);
    public boolean certifyInputWithPartner(PaymentRequest paymentRequest);
    public void retryPartnerApi(String paymentId, String apiToInvoke) throws JSONException;
}
