package com.PaymentEngine.controller;
import com.PaymentEngine.exceptions.customExceptions.ApplicationException;
import com.PaymentEngine.model.PaymentRequest;
import com.PaymentEngine.model.PaymentResponse;
import com.PaymentEngine.service.PayoutService;
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
    public ResponseEntity<PaymentResponse> processPayout(@RequestBody @Valid PaymentRequest paymentRequest) throws JsonProcessingException {
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paymentRequest));

            // Send request to asynchronous service for background processing
            payoutService.processPayoutAsync(paymentRequest);

            // Return a response indicating the transaction is being processed
            PaymentResponse paymentResponse = new PaymentResponse("Success", 202, "Payout request received and is being processed");

            return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.ACCEPTED);
        }
        catch (ApplicationException ex) {
            throw ex;
        }
        catch(Exception ex) {
            throw new RuntimeException(ex); // wrap and propagate
        }
    }

    @PostMapping("/status")
    public ResponseEntity<PaymentResponse> getTransactionStatus(@RequestBody @Valid PaymentRequest paymentRequest) throws JsonProcessingException {
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paymentRequest));

            // Send request to asynchronous service for background processing
//            payoutService.processPayoutAsync(paymentRequest);

            // Return a response indicating the transaction is being processed
            PaymentResponse paymentResponse = new PaymentResponse("Success", 200, "DELIVERED");

            return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.ACCEPTED);
        }
        catch (ApplicationException ex) {
            throw ex;
        }
        catch(Exception ex) {
            throw new RuntimeException(ex); // wrap and propagate
        }
    }
}
