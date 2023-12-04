package com.scalable.payment.controller;

import com.scalable.payment.model.Payment;
import com.scalable.payment.service.PaymentService;

import io.micrometer.core.instrument.MeterRegistry;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    private final MeterRegistry registry;

    public PaymentController(MeterRegistry registry) {
        this.registry = registry;
    }

    private final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/create-default/{username}")
    public ResponseEntity<String> createNewDefaultPayment(@PathVariable String username)
    {
        paymentService.createDefaultPayment(username);

        registry.counter("creditUsername.total", "username", username).increment();
        LOG.info("New user credit " + username + " successfully created");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/{username}/{credits}")
    public ResponseEntity<String> createNewPayment(@PathVariable String username, @PathVariable Double credits)
    {
        paymentService.createNewPayment(username, credits);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balance/{username}")
    public ResponseEntity<Double> getBalance(@PathVariable String username) {
        Payment retrievedUsername = paymentService.getUser(username).orElse(null);
        if (retrievedUsername != null) {
            return ResponseEntity.ok(retrievedUsername.getBalance());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
