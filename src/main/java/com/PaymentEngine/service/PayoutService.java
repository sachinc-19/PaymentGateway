package com.PaymentEngine.service;
import com.PaymentEngine.engine.PayoutEngine;
import com.PaymentEngine.exceptions.customExceptions.ApplicationException;
import com.PaymentEngine.model.PaymentRequest;
import com.PaymentEngine.repository.dbOperation.SetDbObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ComponentScan
@Service
public class PayoutService {

    @Autowired
    private UserService userService;
    @Autowired
    private PayoutEngine payoutEngine;
    @Autowired
    private SetDbObjects setDbObjects;

    private static final Logger logger = LoggerFactory.getLogger(PayoutService.class);

    public void processPayoutAsync(PaymentRequest paymentRequest) {
        try {
            // Step 1: Perform authentication and authorization
            userService.authenticate(paymentRequest.getCredential());

            // Step 2: Save the transaction in database
            setDbObjects.save(paymentRequest);

            // Step 2: Pass the request to the PaymentEngine for routing and validation
            payoutEngine.handlePayout(paymentRequest);

        }
        catch (ApplicationException ex) {
            logger.error("ServiceException: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
