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
        payoutTxn.setCorrelationid(payoutRequest.getPayoutTxnDetails().getCorrelationId());
        payoutTxn.setMtcn(payoutRequest.getPayoutTxnDetails().getMtcn());
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
        payoutTxnDetails.setCorrelationid(payoutRequest.getPayoutTxnDetails().getCorrelationId());
        payoutTxnDetails.setMtcn(payoutRequest.getPayoutTxnDetails().getMtcn());
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

        Amount amount = new Amount();

        AmountId amountId = new AmountId(payoutRequest.getPayoutTxnDetails().getPayoutId(), "SEND");
        amount.setAmountId(amountId);

        amount.setPayoutTxnDetails(payoutTxnDetails);
        amount.setCurrencycode(payoutRequest.getPayoutTxnDetails().getTransferDetails().getSendAmount().getCurrencyCode());
        amount.setValue(payoutRequest.getPayoutTxnDetails().getTransferDetails().getSendAmount().getValue());
        // VALUEMULTIPLIER
        // FXRATE
        // FXRATEMULTIPLIER
        amount.setModifiedon(LocalDateTime.now());

        System.out.println("Saving amount Object in DB");

        amountRepository.save(amount);

    }
}
