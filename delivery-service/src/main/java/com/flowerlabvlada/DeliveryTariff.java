package com.flowerlabvlada;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class DeliveryTariff {

    @Id
    @GeneratedValue
    private Long id;

    private String cityKeyword;
    private Double price;

    public DeliveryTariff() {}

    public DeliveryTariff(String cityKeyword, Double price) {
        this.cityKeyword = cityKeyword;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCityKeyword() { return cityKeyword; }
    public void setCityKeyword(String cityKeyword) { this.cityKeyword = cityKeyword; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}