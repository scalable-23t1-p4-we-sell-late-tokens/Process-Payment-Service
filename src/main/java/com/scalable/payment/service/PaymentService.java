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

    public Optional<Payment> getUser(String username) {
        return createPaymentRepository.findByUsername(username);
    }

}
