package com.scalable.payment.exception.custom;

import com.scalable.payment.exception.RuntimeExceptionWithPayload;
import com.scalable.payment.type.json.ProgressJSON;
import com.scalable.payment.type.json.RollbackJSON;

public class ItemNotFoundException extends RuntimeExceptionWithPayload {
    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(RollbackJSON message) {
        super(message);
    }

    public ItemNotFoundException(ProgressJSON message) {
        super(message);
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
