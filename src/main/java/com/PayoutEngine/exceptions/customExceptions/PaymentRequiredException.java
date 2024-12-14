package com.PayoutEngine.exceptions.customExceptions;

public class PaymentRequiredException extends RuntimeException {
    public PaymentRequiredException(String message) {
        super(message);
    }
}
