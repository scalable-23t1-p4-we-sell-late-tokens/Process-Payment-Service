package com.scalable.payment.exception.custom;

import com.scalable.payment.exception.RuntimeExceptionWithPayload;
import com.scalable.payment.type.json.RollbackJSON;
import lombok.Getter;
import lombok.Setter;


public class TimeOutException extends RuntimeExceptionWithPayload {
    public TimeOutException() {
        super();
    }

    public TimeOutException(RollbackJSON message) {
        super(message);
    }

    public TimeOutException(String message) {
        super(message);
    }

    public TimeOutException(String message, Throwable cause) {
        super(message, cause);
    }
}