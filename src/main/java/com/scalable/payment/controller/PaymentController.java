package com.scalable.payment.controller;
import com.scalable.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<String> createNewPayment(@RequestParam String username,
                                                 @RequestParam String product,
                                                 @RequestParam Integer amount,
                                                 @RequestParam Double price)
    {
        paymentService.CreateNewPayment(username, product, amount, price);
        return ResponseEntity.ok().build();
    }
}
