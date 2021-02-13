package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private float price;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<OrderLine> lines = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new IllegalArgumentException("Incorrect product name");
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if (price <= 0)
            throw new IllegalArgumentException("Incorrect price");
        this.price = price;
    }

    public Set<OrderLine> getLines() {
        return lines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Float.compare(product.price, price) == 0 && Objects.equals(name, product.name) && Objects.equals(lines, product.lines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, lines);
    }
}
