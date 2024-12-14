package com.PayoutEngine.service;
import com.PayoutEngine.engine.PayoutEngine;
import com.PayoutEngine.exceptions.customExceptions.ApplicationException;
import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.repository.dbOperation.SetDbObjects;
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

    public void processPayoutAsync(PayoutRequest payoutRequest) {
        try {
            // Step 1: Perform authentication and authorization
            userService.authenticate(payoutRequest.getCredential());

            // Step 2: Save the transaction in database
            setDbObjects.save(payoutRequest);

            // Step 2: Pass the request to the PayoutEngine for routing and validation
            payoutEngine.handlePayout(payoutRequest);

        }
        catch (ApplicationException ex) {
            logger.error("ServiceException: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
