package com.scalable.payment.exception.custom;


public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists() {
        super();
    }

    public UserAlreadyExists(String message) {
        super(message);
    }

    public UserAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
