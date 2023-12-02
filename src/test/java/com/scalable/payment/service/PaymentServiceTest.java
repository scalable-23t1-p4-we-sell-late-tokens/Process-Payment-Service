package com.scalable.payment.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.scalable.payment.model.Payment;
import com.scalable.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void testCreateDefaultPayment() {
        String username = "Domo";
        Payment newOrder = new Payment(username);

        when(paymentRepository.save(any(Payment.class))).thenReturn(newOrder);

        paymentService.createDefaultPayment(username);

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    public void testCreateNewPayment() {
        String username = "DomoDesu";
        Double credits = 100.0;
        Payment newOrder = new Payment(username, credits);

        // Mock the behavior of the repository
        when(paymentRepository.save(any(Payment.class))).thenReturn(newOrder);

        // Call the service method
        paymentService.createNewPayment(username, credits);

        // Verify that the repository save method was called with the correct parameter
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    public void testGetUser() {
        String username = "DomoTheDomo";
        Payment payment = new Payment(username);
        Optional<Payment> optionalPayment = Optional.of(payment);

        // Mock the behavior of the repository
        when(paymentRepository.findByUsername(username)).thenReturn(optionalPayment);

        // Call the service method
        Optional<Payment> result = paymentService.getUser(username);

        // Verify the result
        assertTrue(result.isPresent());
        assertEquals(payment, result.get());

        // Verify that the repository findByUsername method was called with the correct parameter
        verify(paymentRepository, times(1)).findByUsername(username);
    }
}
