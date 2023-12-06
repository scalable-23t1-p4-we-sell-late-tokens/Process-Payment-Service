package com.scalable.payment.component.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalable.payment.exception.ExceptionWithPayload;
import com.scalable.payment.exception.RuntimeExceptionWithPayload;
import com.scalable.payment.exception.custom.*;
import com.scalable.payment.service.PaymentService;
import com.scalable.payment.type.json.JSONBuilder;
import com.scalable.payment.type.json.JSONMessageTypeFactory;
import com.scalable.payment.type.json.ProgressJSON;
import com.scalable.payment.type.json.RollbackJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

// TODO: Figure out and implement the timeout mechanism
// Add a way to add a custom message to message_response
// Fix exception not holding a value
class RuntimeExceptionRollbacker<T extends RuntimeExceptionWithPayload> {

    T exception;
    PaymentService service;

    protected RuntimeExceptionRollbacker(T exception, PaymentService service) {
        this.exception = exception;
        this.service = service;
    }

    protected void rollback() {
        service.rollback(exception.getUsername(), exception.getItem_name(),
                exception.getAmount());
        JSONBuilder response = new JSONBuilder();
        response.addField("username", exception.getUsername())
                .addField("order_id", exception.getOrder_id())
                .addField("message_response", exception.getMessage_response());
        try {
            service.sendRollbackSignal(response.buildAsString());
            System.out.println("Rollback complete");
        }
        catch (UnknownException unknownException) {
            System.err.println("Failed to rollback " + unknownException.getMessage());
        }
    }
}

class ExceptionRollbacker<T extends ExceptionWithPayload> {

    T exception;
    PaymentService service;

    protected ExceptionRollbacker(T exception, PaymentService service) {
        this.exception = exception;
        this.service = service;
    }

    protected void rollback() {
        service.rollback(exception.getUsername(), exception.getItem_name(),
                exception.getAmount());
        JSONBuilder response = new JSONBuilder();
        response.addField("username", exception.getUsername())
                .addField("order_id", exception.getOrder_id())
                .addField("message_response", exception.getMessage_response());
        try {
            service.sendRollbackSignal(response.buildAsString());
            System.out.println("Rollback complete");
        }
        catch (UnknownException unknownException) {
            System.err.println("Failed to rollback " + unknownException.getMessage());
        }
    }
}

@Component
public class RedisProgressChannel implements MessageListener {
    private final ObjectMapper objectMapper;

    @Autowired
    PaymentService paymentService;

    public RedisProgressChannel(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        boolean messageProcessed = false;
        String payload = new String(message.getBody());
        JSONMessageTypeFactory jsonMessageTypeFactory = new JSONMessageTypeFactory();
        try {
            ProgressJSON json = (ProgressJSON) jsonMessageTypeFactory
                    .createMessage(payload, ProgressJSON.class);


            String username = json.getUsername();
            String orderID = json.getOrder_id();
            String itemName = json.getItem_name();
            long amount = json.getAmount();
            String messageFlag = json.getMessage_flag();

            paymentService.orderItem(json);
            messageProcessed = true;

            JSONBuilder response = new JSONBuilder();
            response.addField("username", username)
                    .addField("order_id", orderID)
                    .addField("item_name", itemName)
                    .addField("amount", amount)
                    .addField("message_response", messageFlag);

            if(messageFlag.equals("payment")) {
                throw new ForceRollbackException(response.buildAsClass(RollbackJSON.class));
            }

            paymentService.sendProgressSignal(response.buildAsString());
            messageProcessed = false;

        } catch (ForceRollbackException forceRollbackException) {
            System.err.println("The message was flagged for rollback demonstration, you can chill out (unless unintented): "
                    + forceRollbackException.getMessage());
            System.out.println("Beginning rollback process");
            forceRollbackException.setMessage_response("FORCED");
            RuntimeExceptionRollbacker<ForceRollbackException> rollbacker =
                    new RuntimeExceptionRollbacker<>(forceRollbackException, paymentService);
            rollbacker.rollback();

        } catch (InsufficientFundException insufficientFundException) {
            System.err.println("You don't got the cash: " + insufficientFundException.getMessage());
            JSONBuilder response = new JSONBuilder();
            response.addField("username", insufficientFundException.getUsername())
                .addField("order_id", insufficientFundException.getOrder_id())
                .addField("message_response", "INSUFFICIENT_FUND");
            try {
                paymentService.sendRollbackSignal(response.buildAsString());
                System.out.println("Rollback message sent");
            }
            catch (UnknownException unknownException) {
                System.err.println("Failed to rollback " + unknownException.getMessage());
        }

        } catch (ItemNotFoundException itemNotFoundException) {
            System.err.println("Item doesn't exist: " + itemNotFoundException.getMessage());
        } catch (UserNotFoundException userNotFoundException) {
            System.err.println("User doesn't exist: " + userNotFoundException.getMessage());
        } catch (TimeOutException timeOutException) {
            System.err.println("The service has reached timeout period: " + timeOutException.getMessage());
            if (messageProcessed) {
                System.out.println("Beginning rollback process");
                timeOutException.setMessage_response("TIMEOUT");
                RuntimeExceptionRollbacker<TimeOutException> rollbacker =
                        new RuntimeExceptionRollbacker<>(timeOutException, paymentService);
                rollbacker.rollback();

            }
        } catch (UnknownException unknownException) {
            System.err.println("Exception occurred, read the error message: " + unknownException.getMessage());
            if (messageProcessed) {
                System.out.println("Beginning rollback process");
                unknownException.setMessage_response("UNKNOWN");
                ExceptionRollbacker<UnknownException> rollbacker =
                        new ExceptionRollbacker<>(unknownException, paymentService);
                rollbacker.rollback();
            }
        }
    }
}
