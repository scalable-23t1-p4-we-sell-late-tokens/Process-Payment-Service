package com.scalable.payment.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scalable.payment.model.Payment;

import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String> {
    Optional<Payment> findByUsername(String username);
}
