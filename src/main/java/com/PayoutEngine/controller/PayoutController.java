package com.PayoutEngine.controller;
import com.PayoutEngine.exceptions.customExceptions.ApplicationException;
import com.PayoutEngine.model.PayoutRequest;
import com.PayoutEngine.model.PayoutResponse;
import com.PayoutEngine.service.PayoutService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payout")
public class PayoutController { // Handles incoming requests and interacts with service layer

    @Autowired
    private PayoutService payoutService;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/process")
    public ResponseEntity<PayoutResponse> processPayout(@RequestBody @Valid PayoutRequest payoutRequest) throws JsonProcessingException {
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payoutRequest));

            // Send request to asynchronous service for background processing
            payoutService.processPayoutAsync(payoutRequest);

            // Return a response indicating the transaction is being processed
            PayoutResponse payoutResponse = new PayoutResponse("Success", 202, "Payout request received and is being processed");

            return new ResponseEntity<PayoutResponse>(payoutResponse , HttpStatus.ACCEPTED);
        }
        catch (ApplicationException ex) {
            throw ex;
        }
        catch(Exception ex) {
            throw new RuntimeException(ex); // wrap and propagate
        }
    }

    @PostMapping("/status")
    public ResponseEntity<PayoutResponse> getTransactionStatus(@RequestBody @Valid PayoutRequest payoutRequest) throws JsonProcessingException {
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payoutRequest));

            // Send request to asynchronous service for background processing
//            payoutService.processPayoutAsync(payoutRequest);

            // Return a response indicating the transaction is being processed
            PayoutResponse payoutResponse = new PayoutResponse("Success", 200, "DELIVERED");

            return new ResponseEntity<PayoutResponse>(payoutResponse , HttpStatus.ACCEPTED);
        }
        catch (ApplicationException ex) {
            throw ex;
        }
        catch(Exception ex) {
            throw new RuntimeException(ex); // wrap and propagate
        }
    }
}
