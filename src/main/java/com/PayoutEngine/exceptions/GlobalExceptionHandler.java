package com.PayoutEngine.exceptions;

import com.PayoutEngine.exceptions.customExceptions.*;
import com.PayoutEngine.model.ErrorHandler;
import com.PayoutEngine.model.PayoutResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ErrorHandler errorHandler;

    // Handle 400 - Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<PayoutResponse> handleBadRequest(IllegalArgumentException ex) {
        PayoutResponse response = new PayoutResponse("error", HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PayoutResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            errors.put(error.getField(), error.getDefaultMessage());
//        });
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorHandler.addError(error.getField(), error.getDefaultMessage());
        });
        PayoutResponse response = new PayoutResponse("error", HttpStatus.BAD_REQUEST.value(), "One or more validations failed");
        response.setValidationErrors(errorHandler.getErrors());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle 402 - Payment Required
    @ExceptionHandler(PaymentRequiredException.class)
    public ResponseEntity<PayoutResponse> handlePaymentRequired(PaymentRequiredException ex) {
        PayoutResponse response = new PayoutResponse("error", HttpStatus.PAYMENT_REQUIRED.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.PAYMENT_REQUIRED);
    }

    // Handle any uncaught exceptions
    // Handle 500 - Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<PayoutResponse> handleGeneralError(Exception ex) {
        PayoutResponse response = new PayoutResponse("error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle ServiceException
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<PayoutResponse> handleServiceException(ServiceException ex) {
        PayoutResponse response = new PayoutResponse("error", ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode()));
    }

    // Handle EngineException
    @ExceptionHandler(EngineException.class)
    public ResponseEntity<PayoutResponse> handleEngineException(EngineException ex) {
        PayoutResponse response = new PayoutResponse("error", ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode()));
    }

    // Handle ProcessorException
    @ExceptionHandler(ProcessorException.class)
    public ResponseEntity<PayoutResponse> handleProcessorException(ProcessorException ex) {
        PayoutResponse response = new PayoutResponse("error", ex.getErrorCode(), ex.getMessage());
        response.setValidationErrors(errorHandler.getErrors());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<PayoutResponse> handleApplicationException(ApplicationException ex) {
        PayoutResponse response = new PayoutResponse("error", ex.getErrorCode(), ex.getMessage());
        response.setValidationErrors(errorHandler.getErrors());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode()));
    }
}
