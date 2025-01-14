package com.PayoutEngine.repository.dbOperation;

import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.repository.dao.AmountRepository;
import com.PayoutEngine.repository.dao.PaymentTransactionRepository;
import com.PayoutEngine.repository.dao.PspDetailsRepository;
import com.PayoutEngine.repository.dao.PaymentTransactionDetailsRepository;
import com.PayoutEngine.repository.entities.*;
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

    public void save(PayoutRequest payoutRequest) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();

        paymentTransaction.setPaymentid(payoutRequest.getPaymentDetails().getPaymentId());
        paymentTransaction.setChannel(payoutRequest.getPaymentDetails().getChannel());
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

        paymentTransactionDetails.setPaymentid(payoutRequest.getPaymentDetails().getPaymentId());
        paymentTransactionDetails.setChannel(payoutRequest.getPaymentDetails().getChannel());
        paymentTransactionDetails.setPartnername(payoutRequest.getPaymentDetails().getPspDetails().getPartnerName());
        paymentTransactionDetails.setPartnerreference("");
        paymentTransactionDetails.setSendcountrycode(payoutRequest.getPaymentDetails().getTransferDetails().getSendCountryCode());
        paymentTransactionDetails.setReceivecountrycode(payoutRequest.getPaymentDetails().getTransferDetails().getReceiveCountryCode());
        paymentTransactionDetails.setOverallfx(80);
        paymentTransactionDetails.setOverallfxmultiplier(100);
        paymentTransactionDetails.setPurpose(payoutRequest.getPaymentDetails().getPurpose());
        paymentTransactionDetails.setDeliveryestimate(LocalDateTime.now());
        paymentTransactionDetails.setLocaldeliveryestimate(LocalDateTime.now());
        paymentTransactionDetails.setModifiedon(LocalDateTime.now());
        paymentTransactionDetails.setCreatedon(LocalDateTime.now());


        System.out.println("Saving paymentTransactionDetails Object in DB");
        paymentTransactionDetailsRepository.save(paymentTransactionDetails);

        PspDetails pspDetails = new PspDetails();

        pspDetails.setPaymentid(payoutRequest.getPaymentDetails().getPaymentId());
        pspDetails.setAccountnumber(payoutRequest.getPaymentDetails().getPspDetails().getAccountNumber());
        pspDetails.setPartnername(payoutRequest.getPaymentDetails().getPspDetails().getPartnerName());
        pspDetails.setBankname(payoutRequest.getPaymentDetails().getPspDetails().getBankName());
        pspDetails.setBankcode(payoutRequest.getPaymentDetails().getPspDetails().getBankCode());
        pspDetails.setBranchcode(payoutRequest.getPaymentDetails().getPspDetails().getBranchCode());
        pspDetails.setBeneficiaryname(payoutRequest.getPaymentDetails().getPspDetails().getBeneficiaryName());
        pspDetails.setModifiedon(LocalDateTime.now());

        System.out.println("Saving pspDetails Object in DB");
        pspDetailsRepository.save(pspDetails);

        Amount sendAmount = new Amount();

        AmountId sendAmountId = new AmountId(payoutRequest.getPaymentDetails().getPaymentId(), "SEND");
        sendAmount.setAmountId(sendAmountId);

        sendAmount.setPaymentTransactionDetails(paymentTransactionDetails);
        sendAmount.setCurrencycode(payoutRequest.getPaymentDetails().getTransferDetails().getSendAmount().getCurrencyCode());
        sendAmount.setValue(payoutRequest.getPaymentDetails().getTransferDetails().getSendAmount().getValue());AmountId amountId = new AmountId(payoutRequest.getPaymentDetails().getPaymentId(), "SEND");
        sendAmount.setModifiedon(LocalDateTime.now());
        amountRepository.save(sendAmount);
        System.out.println("Saving Send Amount Object in DB");

        Amount receiveAmount = new Amount();
        AmountId receiveAmountId = new AmountId(payoutRequest.getPaymentDetails().getPaymentId(), "RECEIVE");
        receiveAmount.setAmountId(receiveAmountId);

        receiveAmount.setPaymentTransactionDetails(paymentTransactionDetails);
        receiveAmount.setCurrencycode(payoutRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getCurrencyCode());
        receiveAmount.setValue(payoutRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getValue());
        receiveAmount.setModifiedon(LocalDateTime.now());
        // VALUEMULTIPLIER
        // FXRATE
        // FXRATEMULTIPLIER

        System.out.println("Saving receive Amount Object in DB");
        amountRepository.save(receiveAmount);
    }
}
