package com.scalable.payment.repository;

import com.scalable.payment.model.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DataJpaTest
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void testSaveAndFindByUsername() {
        // Save an entity
        Payment entity = new Payment();
        entity.setUsername("Pongku");
        paymentRepository.save(entity);

        // Find the entity by name
        Payment foundEntity = paymentRepository.findByUsername("Pongku").orElse(null);


        // Assertions
        assertNotNull(foundEntity);
        assertEquals("Pongku", foundEntity.getUsername());
    }

    @Test
    public void testFindByNonexistentUsername() {
        // Try to find an entity with a nonexistent name
        Payment foundEntity = paymentRepository.findByUsername("Shadow").orElse(null);

        // Assertions
        assertNull(foundEntity);
    }
}
