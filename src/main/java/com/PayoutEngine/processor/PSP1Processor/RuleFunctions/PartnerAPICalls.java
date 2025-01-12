package com.PayoutEngine.processor.PSP1Processor.RuleFunctions;

import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.processor.PSP1Processor.model.ApiResponse;
import com.PayoutEngine.processor.PaymentServiceProvider;
import com.PayoutEngine.processor.api.PSPApiClient;
import com.PayoutEngine.repository.entities.PayoutTxnDetails;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Api;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
        jsonRequest.put("OrderID", payoutRequest.getPayoutTxnDetails().getPayoutId());
        jsonRequest.put("SenderCountryCode", payoutRequest.getPayoutTxnDetails().getTransferDetails().getSendCountryCode());
        jsonRequest.put("SenderCurrencyCode", payoutRequest.getPayoutTxnDetails().getTransferDetails().getSendAmount().getCurrencyCode());
        jsonRequest.put("SenderAmount", payoutRequest.getPayoutTxnDetails().getTransferDetails().getSendAmount().getValue());
        jsonRequest.put("ReceiverAccountNumber", payoutRequest.getPayoutTxnDetails().getTransferDetails());
        jsonRequest.put("ReceiverCountryCode", payoutRequest.getPayoutTxnDetails().getTransferDetails().getReceiveCountryCode());
        jsonRequest.put("ReceiverCurrencyCode", payoutRequest.getPayoutTxnDetails().getTransferDetails().getReceiveAmount().getCurrencyCode());
        jsonRequest.put("ReceiverAmount", payoutRequest.getPayoutTxnDetails().getTransferDetails().getReceiveAmount().getValue());
        jsonRequest.put("BeneficiaryName", payoutRequest.getPayoutTxnDetails().getPartnerDetails().getBeneficiaryName());
        jsonRequest.put("Purpose", payoutRequest.getPayoutTxnDetails().getPurpose());

        System.out.println("json Request : " + jsonRequest);

        ResponseEntity<?> response = pspApiClient.callApi("sendRemitPSP1", urlParams, jsonRequest, ApiResponse.class);

        ApiResponse body = (ApiResponse) response.getBody();

        responseHandling.actionOnServiceResponse(payoutRequest.getPayoutTxnDetails().getPayoutId(), "REMIT", body);
    }

    public void statusPaymentAPI(PayoutRequest payoutRequest) throws JSONException {
        System.out.println("-----------------Inside PartnerAPICalls statusPaymentAPI PSP1-----------------");
        Map<String, String> urlParams = Map.of("id", payoutRequest.getPayoutTxnDetails().getPayoutId());

        ResponseEntity<?> response = pspApiClient.callApi("getStatusPSP1", urlParams, null, ApiResponse.class);

        ApiResponse body = (ApiResponse) response.getBody();

        responseHandling.actionOnServiceResponse(payoutRequest.getPayoutTxnDetails().getPayoutId(), "STATUS", body);
    }
}
