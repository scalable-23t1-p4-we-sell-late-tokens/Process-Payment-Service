package com.scalable.payment.service;

import com.scalable.payment.model.Payment;
import com.scalable.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PaymentService {
    @Autowired
    private PaymentRepository createPaymentRepository;

    public void createDefaultPayment(String username) {
        Payment newOrder = new Payment(username);
        createPaymentRepository.save(newOrder);
    }

    public void createNewPayment(String username, Double credits) {
        Payment newOrder = new Payment(username, credits);

        createPaymentRepository.save(newOrder);
    }

    public Optional<Payment> getUser(String username) {
        return createPaymentRepository.findByUsername(username);
    }

}
