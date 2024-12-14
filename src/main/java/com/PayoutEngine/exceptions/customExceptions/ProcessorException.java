package com.PayoutEngine.exceptions.customExceptions;

public class ProcessorException extends ApplicationException {
    public ProcessorException(String message, int errorCode) {
        super(message, errorCode);
    }
}
