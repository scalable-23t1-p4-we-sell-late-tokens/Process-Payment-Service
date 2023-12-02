package com.scalable.payment.controller;
import com.scalable.payment.model.Payment;
import com.scalable.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("/create-default")
    public ResponseEntity<String> createNewDefaultPayment(@RequestParam String username)
    {
        paymentService.createDefaultPayment(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNewPayment(@RequestParam String username, @RequestParam Double credits)
    {
        paymentService.createNewPayment(username, credits);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestParam String username) {
        Payment retrievedUsername = paymentService.getUser(username).orElse(null);
        if (retrievedUsername != null) {
            return ResponseEntity.ok(retrievedUsername.getBalance());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
