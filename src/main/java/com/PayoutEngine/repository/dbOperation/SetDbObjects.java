package com.PayoutEngine.repository.dbOperation;

import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.repository.dao.AmountRepository;
import com.PayoutEngine.repository.dao.PartnerDetailsRepository;
import com.PayoutEngine.repository.dao.PayoutTxnRepository;
import com.PayoutEngine.repository.dao.PayoutTxnDetailsRepository;
import com.PayoutEngine.repository.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class SetDbObjects {
    @Autowired
    private PayoutTxnRepository payoutTxnRepository;

    @Autowired
    private PayoutTxnDetailsRepository payoutTxnDetailsRepository;

    @Autowired
    private PartnerDetailsRepository partnerDetailsRepository;

    @Autowired
    private AmountRepository amountRepository;

    public void save(PayoutRequest payoutRequest) {
        PayoutTxn payoutTxn = new PayoutTxn();

        payoutTxn.setPayoutid(payoutRequest.getPayoutTxnDetails().getPayoutId());
        payoutTxn.setChannel(payoutRequest.getPayoutTxnDetails().getChannel());
        payoutTxn.setState("PAYOUT");
        payoutTxn.setSubstate("INITIATED");
        payoutTxn.setStatus("In Progress");
        payoutTxn.setDescription("work in progress");
        payoutTxn.setReasoncode("");
        payoutTxn.setLastupdated(LocalDateTime.now());
        payoutTxn.setModifiedon(LocalDateTime.now());
        payoutTxn.setCreatedon(LocalDateTime.now());

        System.out.println("Saving payoutTxn Object in DB");
        payoutTxnRepository.save(payoutTxn);

        PayoutTxnDetails payoutTxnDetails = new PayoutTxnDetails();

        payoutTxnDetails.setPayoutid(payoutRequest.getPayoutTxnDetails().getPayoutId());
        payoutTxnDetails.setChannel(payoutRequest.getPayoutTxnDetails().getChannel());
        payoutTxnDetails.setPartnername(payoutRequest.getPayoutTxnDetails().getPartnerDetails().getPartnerName());
        payoutTxnDetails.setPartnerreference("");
        payoutTxnDetails.setSendcountrycode(payoutRequest.getPayoutTxnDetails().getTransferDetails().getSendCountryCode());
        payoutTxnDetails.setReceivecountrycode(payoutRequest.getPayoutTxnDetails().getTransferDetails().getReceiveCountryCode());
        payoutTxnDetails.setOverallfx(80);
        payoutTxnDetails.setOverallfxmultiplier(100);
        payoutTxnDetails.setPurpose(payoutRequest.getPayoutTxnDetails().getPurpose());
        payoutTxnDetails.setDeliveryestimate(LocalDateTime.now());
        payoutTxnDetails.setLocaldeliveryestimate(LocalDateTime.now());
        payoutTxnDetails.setModifiedon(LocalDateTime.now());
        payoutTxnDetails.setCreatedon(LocalDateTime.now());


        System.out.println("Saving payoutTxnDetails Object in DB");
        payoutTxnDetailsRepository.save(payoutTxnDetails);

        PartnerDetails partnerDetails = new PartnerDetails();

        partnerDetails.setPayoutid(payoutRequest.getPayoutTxnDetails().getPayoutId());
        partnerDetails.setAccountnumber(payoutRequest.getPayoutTxnDetails().getPartnerDetails().getAccountNumber());
        partnerDetails.setPartnername(payoutRequest.getPayoutTxnDetails().getPartnerDetails().getPartnerName());
        partnerDetails.setBankname(payoutRequest.getPayoutTxnDetails().getPartnerDetails().getBankName());
        partnerDetails.setBankcode(payoutRequest.getPayoutTxnDetails().getPartnerDetails().getBankCode());
        partnerDetails.setBranchcode(payoutRequest.getPayoutTxnDetails().getPartnerDetails().getBranchCode());
        partnerDetails.setBeneficiaryname(payoutRequest.getPayoutTxnDetails().getPartnerDetails().getBeneficiaryName());
        partnerDetails.setModifiedon(LocalDateTime.now());

        System.out.println("Saving partnerDetails Object in DB");
        partnerDetailsRepository.save(partnerDetails);

        Amount sendAmount = new Amount();

        AmountId sendAmountId = new AmountId(payoutRequest.getPayoutTxnDetails().getPayoutId(), "SEND");
        sendAmount.setAmountId(sendAmountId);

        sendAmount.setPayoutTxnDetails(payoutTxnDetails);
        sendAmount.setCurrencycode(payoutRequest.getPayoutTxnDetails().getTransferDetails().getSendAmount().getCurrencyCode());
        sendAmount.setValue(payoutRequest.getPayoutTxnDetails().getTransferDetails().getSendAmount().getValue());AmountId amountId = new AmountId(payoutRequest.getPayoutTxnDetails().getPayoutId(), "SEND");
        sendAmount.setModifiedon(LocalDateTime.now());
        amountRepository.save(sendAmount);
        System.out.println("Saving Send Amount Object in DB");

        Amount receiveAmount = new Amount();
        AmountId receiveAmountId = new AmountId(payoutRequest.getPayoutTxnDetails().getPayoutId(), "RECEIVE");
        receiveAmount.setAmountId(receiveAmountId);

        receiveAmount.setPayoutTxnDetails(payoutTxnDetails);
        receiveAmount.setCurrencycode(payoutRequest.getPayoutTxnDetails().getTransferDetails().getReceiveAmount().getCurrencyCode());
        receiveAmount.setValue(payoutRequest.getPayoutTxnDetails().getTransferDetails().getReceiveAmount().getValue());
        receiveAmount.setModifiedon(LocalDateTime.now());
        // VALUEMULTIPLIER
        // FXRATE
        // FXRATEMULTIPLIER

        System.out.println("Saving receive Amount Object in DB");
        amountRepository.save(receiveAmount);
    }
}
