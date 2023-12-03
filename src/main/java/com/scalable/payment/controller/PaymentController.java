package com.scalable.payment.controller;
import com.scalable.payment.model.Payment;
import com.scalable.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("/create-default/{username}")
    public ResponseEntity<String> createNewDefaultPayment(@PathVariable String username)
    {
        paymentService.createDefaultPayment(username);
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
