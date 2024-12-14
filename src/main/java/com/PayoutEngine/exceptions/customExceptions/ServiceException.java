package com.PayoutEngine.exceptions.customExceptions;

// Specific exceptions for each layer
public class ServiceException extends ApplicationException {
    public ServiceException(String message, int errorCode) {
        super(message, errorCode);
    }
}
