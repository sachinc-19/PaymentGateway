package com.PayoutEngine.processor.PSP1Processor.RuleFunctions;

import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.processor.PSP1Processor.model.ApiResponse;
import com.PayoutEngine.processor.PaymentServiceProvider;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean validatePaymentAPI(PayoutRequest payoutRequest) {
        System.out.println("calling validatePaymentAPI from psp1");
        responseHandling.actionOnValidateResponse();
        return true;
    }

    public void commitPaymentAPI(PayoutRequest payoutRequest) {
        System.out.println("calling commitPaymentAPI from psp1");
        responseHandling.actionOnServiceResponse();
    }

    public void statusPaymentAPI(PayoutRequest payoutRequest) {
        System.out.println("calling statusPaymentAPI from psp1");
        responseHandling.actionOnServiceResponse();
    }
}
