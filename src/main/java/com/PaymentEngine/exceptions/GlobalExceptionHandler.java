package com.PaymentEngine.exceptions;

import com.PaymentEngine.exceptions.customExceptions.*;
import com.PaymentEngine.model.ErrorHandler;
import com.PaymentEngine.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ErrorHandler errorHandler;

    // Handle 400 - Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<PaymentResponse> handleBadRequest(IllegalArgumentException ex) {
        PaymentResponse response = new PaymentResponse("error", HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PaymentResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            errors.put(error.getField(), error.getDefaultMessage());
//        });
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorHandler.addError(error.getField(), error.getDefaultMessage());
        });
        PaymentResponse response = new PaymentResponse("error", HttpStatus.BAD_REQUEST.value(), "One or more validations failed");
        response.setValidationErrors(errorHandler.getErrors());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle 402 - Payment Required
    @ExceptionHandler(PaymentRequiredException.class)
    public ResponseEntity<PaymentResponse> handlePaymentRequired(PaymentRequiredException ex) {
        PaymentResponse response = new PaymentResponse("error", HttpStatus.PAYMENT_REQUIRED.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.PAYMENT_REQUIRED);
    }

    // Handle any uncaught exceptions
    // Handle 500 - Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<PaymentResponse> handleGeneralError(Exception ex) {
        PaymentResponse response = new PaymentResponse("error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle ServiceException
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<PaymentResponse> handleServiceException(ServiceException ex) {
        PaymentResponse response = new PaymentResponse("error", ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode()));
    }

    // Handle EngineException
    @ExceptionHandler(EngineException.class)
    public ResponseEntity<PaymentResponse> handleEngineException(EngineException ex) {
        PaymentResponse response = new PaymentResponse("error", ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode()));
    }

    // Handle ProcessorException
    @ExceptionHandler(ProcessorException.class)
    public ResponseEntity<PaymentResponse> handleProcessorException(ProcessorException ex) {
        PaymentResponse response = new PaymentResponse("error", ex.getErrorCode(), ex.getMessage());
        response.setValidationErrors(errorHandler.getErrors());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<PaymentResponse> handleApplicationException(ApplicationException ex) {
        PaymentResponse response = new PaymentResponse("error", ex.getErrorCode(), ex.getMessage());
        response.setValidationErrors(errorHandler.getErrors());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode()));
    }
}
