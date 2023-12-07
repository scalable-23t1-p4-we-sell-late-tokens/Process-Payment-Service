package com.scalable.payment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String username;
    private Double balance;

    public Payment() { }

    public Payment(String username, Double balance) {
        this.username = username;
        this.balance = balance;
    }

    // Let's say that starting balance is 100 unit currency
    public Payment(String username) {
        this.username = username;
        this.balance = 30.0;
    }
}
