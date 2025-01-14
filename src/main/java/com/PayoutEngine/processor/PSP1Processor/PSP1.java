package com.PayoutEngine.processor.PSP1Processor;

import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.processor.PSP1Processor.RuleFunctions.PartnerAPICalls;
import com.PayoutEngine.processor.PaymentServiceProvider;
import com.PayoutEngine.repository.dao.AmountRepository;
import com.PayoutEngine.repository.dao.PspDetailsRepository;
import com.PayoutEngine.repository.dao.PaymentTransactionDetailsRepository;
import com.PayoutEngine.repository.entities.Amount;
import com.PayoutEngine.repository.entities.AmountId;
import com.PayoutEngine.repository.entities.PspDetails;
import com.PayoutEngine.repository.entities.PaymentTransactionDetails;
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
    public boolean certifyInput(PayoutRequest payoutRequest) {
        System.out.println("calling certifyInput from PSP1");
        return true;
    }

    @Override
    public boolean certifyInputWithPartner(PayoutRequest payoutRequest) {
        System.out.println("calling certifyInputWithPartner from PSP1");
        partnerAPICalls.validatePaymentAPI(payoutRequest);
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

        PayoutRequest payoutRequest = new PayoutRequest();
        payoutRequest.setPaymentDetails(new com.PayoutEngine.model.PaymentDetails());
        payoutRequest.getPaymentDetails().setPspDetails(new com.PayoutEngine.model.PspDetails());
        payoutRequest.getPaymentDetails().setTransferDetails(new com.PayoutEngine.model.TransferDetails());
        payoutRequest.getPaymentDetails().getTransferDetails().setSendAmount(new com.PayoutEngine.model.Amount());
        payoutRequest.getPaymentDetails().getTransferDetails().setReceiveAmount(new com.PayoutEngine.model.Amount());

        payoutRequest.getPaymentDetails().setPaymentId(paymentId);
        payoutRequest.getPaymentDetails().getPspDetails().setAccountNumber(pspDetails.getAccountnumber());
        payoutRequest.getPaymentDetails().getPspDetails().setBeneficiaryName(pspDetails.getBeneficiaryname());
        payoutRequest.getPaymentDetails().getTransferDetails().setSendCountryCode(paymentTransactionDetails.getSendcountrycode());
        payoutRequest.getPaymentDetails().getTransferDetails().setReceiveCountryCode(paymentTransactionDetails.getReceivecountrycode());
        payoutRequest.getPaymentDetails().getTransferDetails().getSendAmount().setValue(sendAmount.getValue());
        payoutRequest.getPaymentDetails().getTransferDetails().getSendAmount().setCurrencyCode(sendAmount.getCurrencycode());
        payoutRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().setValue(receiveAmount.getValue());
        payoutRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().setCurrencyCode(receiveAmount.getCurrencycode());
        payoutRequest.getPaymentDetails().setPurpose(paymentTransactionDetails.getPurpose());

        if(apiToInvoke.equals("REMIT")) {
            System.out.println("retrying REMIT API for PSP1 partner");
            partnerAPICalls.commitPaymentAPI(payoutRequest);
        }
        else if (apiToInvoke.equals("STATUS")) {
            System.out.println("retrying STATUS API for PSP1 partner");
            partnerAPICalls.statusPaymentAPI(payoutRequest);
        }
        else {
            System.out.println("Invalid api to invoke");
        }
    }
}
