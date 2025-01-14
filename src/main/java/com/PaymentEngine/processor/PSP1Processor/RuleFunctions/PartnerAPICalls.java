package com.PaymentEngine.processor.PSP1Processor.RuleFunctions;

import com.PaymentEngine.model.PaymentRequest;
import com.PaymentEngine.processor.PSP1Processor.model.ApiResponse;
import com.PaymentEngine.processor.api.PSPApiClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PartnerAPICalls {
    @Autowired
    ResponseHandling responseHandling;
    @Autowired
    private PSPApiClient pspApiClient;

    public boolean validatePaymentAPI(PaymentRequest paymentRequest) {
        System.out.println("calling validatePaymentAPI from psp1");
        responseHandling.actionOnValidateResponse();
        return true;
    }

    public void commitPaymentAPI(PaymentRequest paymentRequest) throws JSONException {
        System.out.println("-----------------Inside PartnerAPICalls commitPaymentAPI PSP1-----------------");
//        PSPApiClient pspApiClient = new PSPApiClient();
//        pspApiClient.callPartnerAPI();

        // Example 1: GET Request
        Map<String, String> urlParams = Map.of("id", "1");
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("OrderID", paymentRequest.getPaymentDetails().getPaymentId());
        jsonRequest.put("SenderCountryCode", paymentRequest.getPaymentDetails().getTransferDetails().getSendCountryCode());
        jsonRequest.put("SenderCurrencyCode", paymentRequest.getPaymentDetails().getTransferDetails().getSendAmount().getCurrencyCode());
        jsonRequest.put("SenderAmount", paymentRequest.getPaymentDetails().getTransferDetails().getSendAmount().getValue());
        jsonRequest.put("ReceiverAccountNumber", paymentRequest.getPaymentDetails().getTransferDetails());
        jsonRequest.put("ReceiverCountryCode", paymentRequest.getPaymentDetails().getTransferDetails().getReceiveCountryCode());
        jsonRequest.put("ReceiverCurrencyCode", paymentRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getCurrencyCode());
        jsonRequest.put("ReceiverAmount", paymentRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getValue());
        jsonRequest.put("BeneficiaryName", paymentRequest.getPaymentDetails().getPspDetails().getBeneficiaryName());
        jsonRequest.put("Purpose", paymentRequest.getPaymentDetails().getPurpose());

        System.out.println("json Request : " + jsonRequest);

        ResponseEntity<?> response = pspApiClient.callApi("sendRemitPSP1", urlParams, jsonRequest, ApiResponse.class);

        ApiResponse body = (ApiResponse) response.getBody();

        responseHandling.actionOnServiceResponse(paymentRequest.getPaymentDetails().getPaymentId(), "REMIT", body);
    }

    public void statusPaymentAPI(PaymentRequest paymentRequest) throws JSONException {
        System.out.println("-----------------Inside PartnerAPICalls statusPaymentAPI PSP1-----------------");
        Map<String, String> urlParams = Map.of("id", paymentRequest.getPaymentDetails().getPaymentId());

        ResponseEntity<?> response = pspApiClient.callApi("getStatusPSP1", urlParams, null, ApiResponse.class);

        ApiResponse body = (ApiResponse) response.getBody();

        responseHandling.actionOnServiceResponse(paymentRequest.getPaymentDetails().getPaymentId(), "STATUS", body);
    }
}
