package com.PaymentEngine.processor.PSP1Processor;

import com.PaymentEngine.model.PaymentRequest;
import com.PaymentEngine.processor.PSP1Processor.RuleFunctions.PartnerAPICalls;
import com.PaymentEngine.processor.PaymentServiceProvider;
import com.PaymentEngine.repository.dao.AmountRepository;
import com.PaymentEngine.repository.dao.PspDetailsRepository;
import com.PaymentEngine.repository.dao.PaymentTransactionDetailsRepository;
import com.PaymentEngine.repository.entities.Amount;
import com.PaymentEngine.repository.entities.AmountId;
import com.PaymentEngine.repository.entities.PspDetails;
import com.PaymentEngine.repository.entities.PaymentTransactionDetails;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("PSP1")
public class PSP1 implements PaymentServiceProvider {
    @Autowired
    PartnerAPICalls partnerAPICalls;
    @Autowired
    PaymentTransactionDetailsRepository paymentTransactionDetailsRepository;
    @Autowired
    PspDetailsRepository pspDetailsRepository;
    @Autowired
    AmountRepository amountRepository;


    @Override
    public boolean certifyInput(PaymentRequest paymentRequest) {
        System.out.println("calling certifyInput from PSP1");
        return true;
    }

    @Override
    public boolean certifyInputWithPartner(PaymentRequest paymentRequest) {
        System.out.println("calling certifyInputWithPartner from PSP1");
        partnerAPICalls.validatePaymentAPI(paymentRequest);
        return true;
    }

    @Override
    public void retryPartnerApi(String paymentId, String apiToInvoke) throws JSONException {
        System.out.println(apiToInvoke + " API to be invoked for PSP1 partner");
        PaymentTransactionDetails paymentTransactionDetails = paymentTransactionDetailsRepository.findById(paymentId).orElse(null);
        PspDetails pspDetails = pspDetailsRepository.findById(paymentId).orElse(null);
        AmountId sendAmountId = new AmountId(paymentId, "SEND");
        Amount sendAmount = amountRepository.findById(sendAmountId).orElse(null);
        AmountId receiveAmountId = new AmountId(paymentId, "RECEIVE");
        Amount receiveAmount = amountRepository.findById(receiveAmountId).orElse(null);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPaymentDetails(new com.PaymentEngine.model.PaymentDetails());
        paymentRequest.getPaymentDetails().setPspDetails(new com.PaymentEngine.model.PspDetails());
        paymentRequest.getPaymentDetails().setTransferDetails(new com.PaymentEngine.model.TransferDetails());
        paymentRequest.getPaymentDetails().getTransferDetails().setSendAmount(new com.PaymentEngine.model.Amount());
        paymentRequest.getPaymentDetails().getTransferDetails().setReceiveAmount(new com.PaymentEngine.model.Amount());

        paymentRequest.getPaymentDetails().setPaymentId(paymentId);
        paymentRequest.getPaymentDetails().getPspDetails().setAccountNumber(pspDetails.getAccountnumber());
        paymentRequest.getPaymentDetails().getPspDetails().setBeneficiaryName(pspDetails.getBeneficiaryname());
        paymentRequest.getPaymentDetails().getTransferDetails().setSendCountryCode(paymentTransactionDetails.getSendcountrycode());
        paymentRequest.getPaymentDetails().getTransferDetails().setReceiveCountryCode(paymentTransactionDetails.getReceivecountrycode());
        paymentRequest.getPaymentDetails().getTransferDetails().getSendAmount().setValue(sendAmount.getValue());
        paymentRequest.getPaymentDetails().getTransferDetails().getSendAmount().setCurrencyCode(sendAmount.getCurrencycode());
        paymentRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().setValue(receiveAmount.getValue());
        paymentRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().setCurrencyCode(receiveAmount.getCurrencycode());
        paymentRequest.getPaymentDetails().setPurpose(paymentTransactionDetails.getPurpose());

        if(apiToInvoke.equals("REMIT")) {
            System.out.println("retrying REMIT API for PSP1 partner");
            partnerAPICalls.commitPaymentAPI(paymentRequest);
        }
        else if (apiToInvoke.equals("STATUS")) {
            System.out.println("retrying STATUS API for PSP1 partner");
            partnerAPICalls.statusPaymentAPI(paymentRequest);
        }
        else {
            System.out.println("Invalid api to invoke");
        }
    }
}
