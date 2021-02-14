package com.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonSerialize
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    private int count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        if (order == null)
            throw new IllegalArgumentException("Incorrect order");
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("Incorrect product");
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count <= 0)
            throw new IllegalArgumentException("Incorrect count");
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return id == orderLine.id && count == orderLine.count && Objects.equals(order, orderLine.order) && Objects.equals(product, orderLine.product);
    }

}
