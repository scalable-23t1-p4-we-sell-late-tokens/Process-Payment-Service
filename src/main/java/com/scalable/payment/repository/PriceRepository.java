package com.scalable.payment.repository;

import com.scalable.payment.model.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PriceRepository extends CrudRepository<Price, Long> {
    Optional<Price> findByItemName(String itemName);
}
