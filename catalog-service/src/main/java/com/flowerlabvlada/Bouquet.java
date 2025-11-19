package com.flowerlabvlada;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bouquets",schema = "catalog")
public class Bouquet extends PanacheEntity {
    public String name;
    public String description;
    public double price;
    public int stockQuantity;

    public Bouquet() {
    }

    public Bouquet(String name, String description, double price, int stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}