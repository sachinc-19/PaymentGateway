package com.PaymentEngine.exceptions.customExceptions;

public class EngineException extends ApplicationException {
    public EngineException(String message, int errorCode) {
        super(message, errorCode);
    }
}
