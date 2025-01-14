package com.PaymentEngine.engine;

import com.PaymentEngine.processor.PaymentServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PSPFactory {

    // Map of PSP codes to their implementations (injected by Spring)
    private final Map<String, PaymentServiceProvider> pspMap;

    @Autowired
    public PSPFactory(Map<String, PaymentServiceProvider> pspMap) {
        this.pspMap = pspMap;
    }

    /**
     * Returns the PSP implementation based on the PSP code.
     *
     * @param pspCode The code for the PSP (e.g., "PSP1", "PSP2").
     * @return The corresponding PaymentServiceProvider instance.
     * @throws PSPNotFoundException If no PSP is found for the provided code.
     */
    public PaymentServiceProvider getPSP(String pspCode) throws PSPNotFoundException {
        PaymentServiceProvider psp = pspMap.get(pspCode);

        if (psp == null) {
            throw new PSPNotFoundException("No PSP found for code: " + pspCode);
        }

        return psp;
    }

    public class PSPNotFoundException extends RuntimeException {
        public PSPNotFoundException(String message) {
            super(message);
        }
    }
}
