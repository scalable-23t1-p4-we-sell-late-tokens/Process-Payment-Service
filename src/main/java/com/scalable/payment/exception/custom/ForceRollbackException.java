package com.scalable.payment.exception.custom;

import com.scalable.payment.exception.RuntimeExceptionWithPayload;
import com.scalable.payment.type.json.ProgressJSON;
import com.scalable.payment.type.json.RollbackJSON;


public class ForceRollbackException extends RuntimeExceptionWithPayload {
    public ForceRollbackException() {
        super();
    }

    public ForceRollbackException(RollbackJSON message) {
        super(message);
    }

    public ForceRollbackException(ProgressJSON message) {
        super(message);
    }

    public ForceRollbackException(String message) {
        super(message);
    }

    public ForceRollbackException(String message, Throwable cause) {
        super(message, cause);
    }
}
