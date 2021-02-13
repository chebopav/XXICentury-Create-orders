package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "made_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String clientName;

    private LocalDate date;

    private String address;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<OrderLine> orderLines = new HashSet<>();

    public Order() {
    }

    public long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        if (clientName == null)
            throw new IllegalArgumentException("Incorrect client name");
        this.clientName = clientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Incorrect date");
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null)
            throw new IllegalArgumentException("Incorrect address");
        this.address = address;
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(clientName, order.clientName) && Objects.equals(date, order.date) && Objects.equals(address, order.address) && Objects.equals(orderLines, order.orderLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientName, date, address, orderLines);
    }
}
