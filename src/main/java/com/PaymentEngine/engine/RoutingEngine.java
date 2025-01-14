package com.PaymentEngine.engine;

import com.PaymentEngine.model.PSPRouting;
import com.PaymentEngine.model.PaymentRequest;
import com.PaymentEngine.processor.PaymentServiceProvider;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;

@Service
public class RoutingEngine {
    @Autowired
    private PSPFactory pspFactory;
    private KieContainer kieContainer;

    public RoutingEngine(PSPFactory pspFactory) {
        this.pspFactory = pspFactory;
        KieServices kieServices = KieServices.Factory.get();

        Resource dt = ResourceFactory.newClassPathResource("PSPRouting.drl.xls", getClass());

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(dt);

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        KieRepository kieRepository = kieServices.getRepository();

        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
        kieContainer = kieServices.newKieContainer(krDefaultReleaseId);
    }

    /**
     * Routes the transaction to the best available PSP based on rules.
     *
     * @param paymentRequest The incoming transaction request.
     * @return The best PSP based on routing rules.
     * @throws RoutingException If no suitable PSP is found.
     */
    public PaymentServiceProvider getBestPspForTransaction(PaymentRequest paymentRequest) throws RoutingException {
        KieSession kieSession = kieContainer.newKieSession();

        PSPRouting pspRouting = new PSPRouting(paymentRequest.getPaymentDetails().getTransferDetails().getSendCountryCode(),
                paymentRequest.getPaymentDetails().getTransferDetails().getReceiveCountryCode(),
                    paymentRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getCurrencyCode(),
                        paymentRequest.getPaymentDetails().getPaymentMethod(),
                            paymentRequest.getPaymentDetails().getTransferDetails().getReceiveAmount().getValue(), null);

        kieSession.insert(pspRouting);
        kieSession.fireAllRules();

        String partner = pspRouting.getPartner();
        System.out.println("partner selected after rule fire: " + partner);
        // UPDATE PSP NAME IN REQUEST
        paymentRequest.getPaymentDetails().getPspDetails().setPartnerName(partner);

        return pspFactory.getPSP(partner);
    }
    /**
     * Compares two PSPs based on business rules. The comparison logic can be customized as per the requirements.
     *
     * @param psp1 First PSP
     * @param psp2 Second PSP
     * @return A negative value if psp1 is preferred over psp2, a positive value if psp2 is preferred over psp1, 0 if equal
     */

    public class RoutingException extends RuntimeException {
        public RoutingException(String message) {
            super(message);
        }
    }
}
