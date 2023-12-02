package com.scalable.payment.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scalable.payment.model.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String> {

}
