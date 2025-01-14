package com.PaymentEngine.repository.dbOperation;

import com.PaymentEngine.model.PaymentRequest;
import com.PaymentEngine.repository.dao.AmountRepository;
import com.PaymentEngine.repository.dao.PaymentTransactionRepository;
import com.PaymentEngine.repository.dao.PspDetailsRepository;
import com.PaymentEngine.repository.dao.PaymentTransactionDetailsRepository;
import com.PaymentEngine.repository.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class SetDbObjects {
    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private PaymentTransactionDetailsRepository paymentTransactionDetailsRepository;

    @Autowired
    private PspDetailsRepository pspDetailsRepository;

    @Autowired
    private AmountRepository amountRepository;

    public void save(PaymentRequest paymentRequest) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();

        paymentTransaction.setPaymentid(paymentRequest.getPaymentDetails().getPaymentId());
        paymentTransaction.setChannel(paymentRequest.getPaymentDetails().getChannel());
        paymentTransaction.setState("PAYOUT");
        paymentTransaction.setSubstate("INITIATED");
        paymentTransaction.setStatus("In Progress");
        paymentTransaction.setDescription("work in progress");
        paymentTransaction.setReasoncode("");
        paymentTransaction.setLastupdated(LocalDateTime.now());
        paymentTransaction.setModifiedon(LocalDateTime.now());
        paymentTransaction.setCreatedon(LocalDateTime.now());

        System.out.println("Saving paymentTransaction Object in DB");
        paymentTransactionRepository.save(paymentTransaction);

        PaymentTransactionDetails paymentTransactionDetails = new PaymentTransactionDetails();

        paymentTransactionDetails.setPaymentid(paymentRequest.getPaymentDetails().getPaymentId());
        paymentTransactionDetails.setChannel(paymentRequest.getPaymentDetails().getChannel());
        paymentTransactionDetails.setPartnername(paymentRequest.getPaymentDetails().getPspDetails().getPartnerName());
        paymentTransactionDetails.setPartnerreference("");
        paymentTransactionDetails.setSendcountrycode(paymentRequest.getPaymentDetails().getTransferDetails().getSendCountryCode());
        paymentTransactionDetails.setReceivecountrycode(paymentRequest.getPaymentDetails().getTransferDetails().getReceiveCountryCode());
        paymentTransactionDetails.setOverallfx(80);
        paymentTransactionDetails.setOverallfxmultiplier(100);
        paymentTransactionDetails.setPurpose(paymentRequest.getPaymentDetails().getPurpose());
        paymentTransactionDetails.setDeliveryestimate(LocalDateTime.now());
        paymentTransactionDetails.setLocaldeliveryestimate(LocalDateTime.now());
        paymentTransactionDetails.setModifiedon(LocalDateTime.now());
        paymentTransactionDetails.setCreatedon(LocalDateTime.now());


        System.out.println("Saving paymentTransactionDetails Object in DB");
        paymentTransactionDetailsRepository.save(paymentTransactionDetails);

        PspDetails pspDetails = new PspDetails();

        pspDetails.setPaymentid(paymentRequest.getPaymentDetails().getPaymentId());
        pspDetails.setAccountnumber(paymentRequest.getPaymentDetails().getPspDetails().getAccountNumber());
        pspDetails.setPartnername(paymentRequest.getPaymentDetails().getPspDetails().getPartnerName());
        pspDetails.setBankname(paymentRequest.getPaymentDetails().getPspDetails().getBankName());
        pspDetails.setBankcode(paymentRequest.getPaymentDetails().getPspDetails().getBankCode());
        pspDetails.setBranchcode(paymentRequest.getPaymentDetails().getPspDetails().getBranchCode());
        pspDetails.setBeneficiaryname(paymentRequest.getPaymentDetails().getPspDetails().getBeneficiaryName());
        pspDetails.setModifiedon(LocalDateTime.now());

        System.out.println("Saving pspDetails Object in DB");
        pspDetailsRepository.save(pspDetails);

        Amount sendAmount = new Amount();

        AmountId sendAmountId = new AmountId(paymentRequest.getPaymentDetails().getPaymentId(), "SEND");
        sendAmount.setAmountId(sendAmountId);

        sendAmount.setPaymentTransactionDetails(paymentTransactionDetails);
        sendAmount.setCurrencycode(paymentRequest.getPaymentDetails().getTransferDetails().getSendAmount().getCurrencyCode());
        sendAmount.setValue(paymentRequest.getPaymentDetails().getTransferDetails().getSendAmount().getValue());AmountId amountId = new AmountId(paymentRequest.getPaymentDetails().getPaymentId(), "SEND");
        sendAmount.setModifiedon(LocalDateTime.now());
        amountRepository.save(sendAmount);
        System.out.println("Saving Send Amount Object in DB");

        Amount receiveAmount = new Amount();
        AmountId receiveAmountId = new AmountId(paymentRequest.getPaymentDetails().getPaymentId(), "RECEIVE");
        receiveAmount.setAmountId(receiveAmountId);

        receiveAmount.setPaymentTransactionDetails(paymentTransactionDetails);
        receiveAmount.setCurrencycode(paymentRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getCurrencyCode());
        receiveAmount.setValue(paymentRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getValue());
        receiveAmount.setModifiedon(LocalDateTime.now());
        // VALUEMULTIPLIER
        // FXRATE
        // FXRATEMULTIPLIER

        System.out.println("Saving receive Amount Object in DB");
        amountRepository.save(receiveAmount);
    }
}
