package com.scalable.payment.component.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalable.payment.exception.custom.UnknownException;
import com.scalable.payment.service.PaymentService;
import com.scalable.payment.type.json.JSONBuilder;
import com.scalable.payment.type.json.JSONMessageTypeFactory;
import com.scalable.payment.type.json.RollbackJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisRollbackChannel implements MessageListener {
    private final ObjectMapper objectMapper;

    @Autowired
    PaymentService paymentService;

    public RedisRollbackChannel(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String payload = new String(message.getBody());
        JSONMessageTypeFactory jsonMessageTypeFactory = new JSONMessageTypeFactory();
        try {
            RollbackJSON json = (RollbackJSON) jsonMessageTypeFactory
                    .createMessage(payload, RollbackJSON.class);


            String username = json.getUsername();
            String orderID = json.getOrder_id();
            String itemName = json.getItem_name();
            long amount = json.getAmount();
            String messageResponse = json.getMessage_response();

            System.out.println("Beginning rollback process");
            paymentService.rollback(username, itemName, amount);

            JSONBuilder response = new JSONBuilder();
            response.addField("username", username)
                    .addField("order_id", orderID)
                    .addField("message_response", messageResponse);

            paymentService.sendRollbackSignal(response.buildAsString());
            System.out.println("Rollback complete");


        } catch (UnknownException unknownException) {
            System.err.println("Unknown Error Occurred: " + unknownException.getMessage());
        }
    }
}
