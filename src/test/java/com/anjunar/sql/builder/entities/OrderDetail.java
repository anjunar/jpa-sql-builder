package com.anjunar.sql.builder.entities;

import jakarta.persistence.*;

@Entity
public class OrderDetail {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
//    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
