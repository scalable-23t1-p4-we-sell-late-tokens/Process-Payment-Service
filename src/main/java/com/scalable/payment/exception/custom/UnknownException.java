package com.scalable.payment.exception.custom;

import com.scalable.payment.exception.ExceptionWithPayload;
import com.scalable.payment.type.json.ProgressJSON;
import com.scalable.payment.type.json.RollbackJSON;

public class UnknownException extends ExceptionWithPayload {
    public UnknownException() {
        super();
    }

    public UnknownException(RollbackJSON message) {
        super(message);
    }

    public UnknownException(ProgressJSON message) {
        super(message);
    }

    public UnknownException(String message) {
        super(message);
    }

    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }
}
