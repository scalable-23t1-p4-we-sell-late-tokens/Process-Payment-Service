package com.scalable.payment.service;

import com.scalable.payment.exception.custom.InsufficientFundException;
import com.scalable.payment.exception.custom.ItemNotFoundException;
import com.scalable.payment.exception.custom.UnknownException;
import com.scalable.payment.exception.custom.UserNotFoundException;
import com.scalable.payment.model.Payment;
import com.scalable.payment.model.Price;
import com.scalable.payment.repository.PaymentRepository;
import com.scalable.payment.repository.PriceRepository;
import com.scalable.payment.service.redis.RedisService;
import com.scalable.payment.type.json.ProgressJSON;

import org.postgresql.translation.messages_es;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PaymentService {
    @Autowired
    private PaymentRepository createPaymentRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private RedisService redisService;

    public void createDefaultPayment(String username) {
        Optional<Payment> entity = createPaymentRepository.findByUsername(username);
        if(!entity.isPresent()) {
            Payment newPayment = new Payment(username);
            createPaymentRepository.save(newPayment);
        }
    }

    public void createNewPayment(String username, Double credits) {
        Optional<Payment> entity = createPaymentRepository.findByUsername(username);
        if(!entity.isPresent()) {
            Payment newPayment = new Payment(username, credits);
            createPaymentRepository.save(newPayment);
        }
    }

    public void createNewItemPrice(String itemName, Double price) {
        Optional<Price> entity = priceRepository.findByItemName(itemName);
        if(!entity.isPresent()) {
            Price newPriceForItem = new Price(itemName, price);
            priceRepository.save(newPriceForItem);
        }
    }


    public void updateItemPrice(String itemName, Double price) {
        Optional<Price> entity = priceRepository.findByItemName(itemName);
        if(entity.isPresent()) {
            Price newPriceForItem = entity.get();
            newPriceForItem.setPrice(price);
            priceRepository.save(newPriceForItem);
        }
    }

    public Double checkPriceOfItem(String itemName) {
        Optional<Price> entity = priceRepository.findByItemName(itemName);
        return entity.map(Price::getPrice).orElse(null);
    }

    public Optional<Payment> getUser(String username) {
        return createPaymentRepository.findByUsername(username);
    }

    public void orderItem(ProgressJSON message) throws ItemNotFoundException,
            InsufficientFundException, UserNotFoundException {
        String username = message.getUsername();
        String itemName = message.getItem_name();
        long amount = message.getAmount();
        Payment user = createPaymentRepository.findByUsername(username).orElse(null);
        Price price = priceRepository.findByItemName(itemName).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User: " + username + " not found");
        }
        else if (price == null) {
            throw new ItemNotFoundException("Item: " + itemName + " not found");
        }
        else {
            if (user.getBalance() < (price.getPrice() * amount)) {
                String exceptionMessage = "More gold is required: (" +
                        ((price.getPrice() * amount) - user.getBalance()) + " more is required)";
                throw new InsufficientFundException(message, exceptionMessage);
            }
            user.setBalance(user.getBalance() - (price.getPrice() * amount));
            createPaymentRepository.save(user);
        }
    }

    public void rollback(String username, String itemName, long amount) throws ItemNotFoundException,
            UserNotFoundException {
        Payment user = createPaymentRepository.findByUsername(username).orElse(null);
        Price price = priceRepository.findByItemName(itemName).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("User: " + username + " not found");
        }
        else if (price == null) {
            throw new ItemNotFoundException("Item: " + itemName + " not found");
        }
        else {
            user.setBalance(user.getBalance() + (price.getPrice() * amount));
            createPaymentRepository.save(user);
        }
    }

    public void sendRollbackSignal(String jsonString) throws UnknownException {
        try {
            redisService.sendMessageToChannel("paymentToOrder", jsonString);
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }
    }

    public void sendProgressSignal(String jsonString) throws UnknownException{
        try {
            redisService.sendMessageToChannel("paymentToInventory", jsonString);
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }
    }

}
