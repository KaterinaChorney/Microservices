package com.flowerlabvlada;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers", schema = "customers")
public class Customer extends PanacheEntityBase {

    @Id
    public Long id;

    public String name;
    public String email;
    public String address;
    public String phone;

    public Customer() {
    }

    public Customer(Long id, String name, String email, String address, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
}