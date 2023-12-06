package com.scalable.payment.exception.custom;

import com.scalable.payment.exception.RuntimeExceptionWithPayload;
import com.scalable.payment.type.json.ProgressJSON;
import com.scalable.payment.type.json.RollbackJSON;

public class InsufficientFundException extends RuntimeExceptionWithPayload {
    public InsufficientFundException() {
        super();
    }

    public InsufficientFundException(RollbackJSON message) {
        super(message);
    }

    public InsufficientFundException(ProgressJSON message) {
        super(message);
    }

    public InsufficientFundException(RollbackJSON message, String errorText) {
        super(message, errorText);
    }

    public InsufficientFundException(ProgressJSON message, String errorText) {
        super(message, errorText);
    }

    public InsufficientFundException(String message) {
        super(message);
    }

    public InsufficientFundException(String message, Throwable cause) {
        super(message, cause);
    }
}
