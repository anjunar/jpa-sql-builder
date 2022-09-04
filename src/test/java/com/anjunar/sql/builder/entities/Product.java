package com.anjunar.sql.builder.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
//    @JoinColumn(name = "SUPPLIER_ID")
    private Supplier supplier;

    private String unit;

    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
