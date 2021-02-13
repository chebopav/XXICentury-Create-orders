package com.services;

import com.entity.Order;
import com.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository repository;
    private OrderLineService orderLineService;

    @Autowired
    public OrderService(OrderRepository repository, OrderLineService orderLineService) {
        this.repository = repository;
        this.orderLineService = orderLineService;
    }

    public boolean addOrder(Order order){
        if (order == null || repository.existsById(order.getId()))
            return false;
        repository.save(order);
        return true;
    }

    public boolean updateOrder(Order order){
        if (!repository.existsById(order.getId()))
            return false;
        repository.save(order);
        return true;
    }

    public boolean deleteOrder(Order order){
        if (!repository.existsById(order.getId()))
            return false;
        repository.delete(order);
        return true;
    }

    public List<Order> getAllOrders(){
        return repository.findAll();
    }

    public Order getOrderById(long id){
        return repository.findById(id).orElseThrow();
    }

    public void deleteOrderById(long id){
        repository.delete(getOrderById(id));
    }

}
