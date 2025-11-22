package com.flowerlabvlada;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue
    private Long id;

    private Long customerId;
    private Double amount;
    private String status;
    private String transactionId;

    public PaymentTransaction() {
    }

    public PaymentTransaction(Long customerId, Double amount, String status, String transactionId) {
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
        this.transactionId = transactionId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
}