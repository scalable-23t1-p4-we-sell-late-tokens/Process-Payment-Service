package com.scalable.payment.exception;

import com.scalable.payment.type.json.ProgressJSON;
import com.scalable.payment.type.json.RollbackJSON;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionWithPayload extends Exception{
    private String username;
    private String order_id;
    private String item_name;
    private long amount;
    private String message_response;
    public ExceptionWithPayload() {
        super();
    }

    public ExceptionWithPayload(RollbackJSON parameters) {
        super();
        this.username = parameters.getUsername();
        this.order_id = parameters.getOrder_id();
        this.item_name = parameters.getItem_name();
        this.amount = parameters.getAmount();
        this.message_response = parameters.getMessage_response();
    }

    public ExceptionWithPayload(ProgressJSON parameters) {
        super();
        this.username = parameters.getUsername();
        this.order_id = parameters.getOrder_id();
        this.item_name = parameters.getItem_name();
        this.amount = parameters.getAmount();
        this.message_response = null;
    }

    public ExceptionWithPayload(ProgressJSON parameters, String message) {
        super(message);
        this.username = parameters.getUsername();
        this.order_id = parameters.getOrder_id();
        this.item_name = parameters.getItem_name();
        this.amount = parameters.getAmount();
        this.message_response = null;
    }

    public ExceptionWithPayload(RollbackJSON parameters, String message) {
        super(message);
        this.username = parameters.getUsername();
        this.order_id = parameters.getOrder_id();
        this.item_name = parameters.getItem_name();
        this.amount = parameters.getAmount();
        this.message_response = parameters.getMessage_response();
    }

    public ExceptionWithPayload(String message) {
        super(message);
    }

    public ExceptionWithPayload(String message, Throwable cause) {
        super(message, cause);
    }
}
