package com.scalable.payment.exception;

import com.scalable.payment.type.json.RollbackJSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuntimeExceptionWithPayload extends RuntimeException{
    private String username;
    private String order_id;
    private String item_name;
    private long amount;
    private String message_response;
    public RuntimeExceptionWithPayload() {
        super();
    }

    public RuntimeExceptionWithPayload(RollbackJSON parameters) {
        super();
        this.username = parameters.getUsername();
        this.order_id = parameters.getOrder_id();
        this.item_name = parameters.getItem_name();
        this.amount = parameters.getAmount();
        this.message_response = parameters.getMessage_response();
    }

    public RuntimeExceptionWithPayload(String message) {
        super(message);
    }

    public RuntimeExceptionWithPayload(String message, Throwable cause) {
        super(message, cause);
    }
}
