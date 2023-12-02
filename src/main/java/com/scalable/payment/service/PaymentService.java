package com.scalable.payment.service;

import com.scalable.payment.model.Payment;
import com.scalable.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {
    @Autowired
    private PaymentRepository createPaymentRepository;

    public void CreateNewPayment(String username, String product, Integer amount, Double price) {
        Payment newOrder = new Payment(username, product, amount, price * amount);

        createPaymentRepository.save(newOrder);
    }
}
