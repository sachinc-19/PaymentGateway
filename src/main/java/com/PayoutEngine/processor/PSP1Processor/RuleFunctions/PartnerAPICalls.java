package com.PayoutEngine.processor.PSP1Processor.RuleFunctions;

import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.processor.PSP1Processor.model.ApiResponse;
import com.PayoutEngine.processor.api.PSPApiClient;
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

    public boolean validatePaymentAPI(PayoutRequest payoutRequest) {
        System.out.println("calling validatePaymentAPI from psp1");
        responseHandling.actionOnValidateResponse();
        return true;
    }

    public void commitPaymentAPI(PayoutRequest payoutRequest) throws JSONException {
        System.out.println("-----------------Inside PartnerAPICalls commitPaymentAPI PSP1-----------------");
//        PSPApiClient pspApiClient = new PSPApiClient();
//        pspApiClient.callPartnerAPI();

        // Example 1: GET Request
        Map<String, String> urlParams = Map.of("id", "1");
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("OrderID", payoutRequest.getPaymentDetails().getPaymentId());
        jsonRequest.put("SenderCountryCode", payoutRequest.getPaymentDetails().getTransferDetails().getSendCountryCode());
        jsonRequest.put("SenderCurrencyCode", payoutRequest.getPaymentDetails().getTransferDetails().getSendAmount().getCurrencyCode());
        jsonRequest.put("SenderAmount", payoutRequest.getPaymentDetails().getTransferDetails().getSendAmount().getValue());
        jsonRequest.put("ReceiverAccountNumber", payoutRequest.getPaymentDetails().getTransferDetails());
        jsonRequest.put("ReceiverCountryCode", payoutRequest.getPaymentDetails().getTransferDetails().getReceiveCountryCode());
        jsonRequest.put("ReceiverCurrencyCode", payoutRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getCurrencyCode());
        jsonRequest.put("ReceiverAmount", payoutRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getValue());
        jsonRequest.put("BeneficiaryName", payoutRequest.getPaymentDetails().getPspDetails().getBeneficiaryName());
        jsonRequest.put("Purpose", payoutRequest.getPaymentDetails().getPurpose());

        System.out.println("json Request : " + jsonRequest);

        ResponseEntity<?> response = pspApiClient.callApi("sendRemitPSP1", urlParams, jsonRequest, ApiResponse.class);

        ApiResponse body = (ApiResponse) response.getBody();

        responseHandling.actionOnServiceResponse(payoutRequest.getPaymentDetails().getPaymentId(), "REMIT", body);
    }

    public void statusPaymentAPI(PayoutRequest payoutRequest) throws JSONException {
        System.out.println("-----------------Inside PartnerAPICalls statusPaymentAPI PSP1-----------------");
        Map<String, String> urlParams = Map.of("id", payoutRequest.getPaymentDetails().getPaymentId());

        ResponseEntity<?> response = pspApiClient.callApi("getStatusPSP1", urlParams, null, ApiResponse.class);

        ApiResponse body = (ApiResponse) response.getBody();

        responseHandling.actionOnServiceResponse(payoutRequest.getPaymentDetails().getPaymentId(), "STATUS", body);
    }
}
