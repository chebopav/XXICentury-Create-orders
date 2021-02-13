package com.services;

import com.entity.OrderLine;
import com.repositories.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService {
    private OrderLineRepository repository;

    @Autowired
    public OrderLineService(OrderLineRepository repository) {
        this.repository = repository;
    }

    public boolean addOrderLine(OrderLine orderLine){
        if (orderLine == null || repository.existsById(orderLine.getId()))
            return false;
        repository.save(orderLine);
        return true;
    }

    public boolean updateOrderLine(OrderLine orderLine){
        if (!repository.existsById(orderLine.getId()))
            return false;
        repository.save(orderLine);
        return true;
    }

    public boolean deleteOrderLine(OrderLine orderLine){
        if (!repository.existsById(orderLine.getId()))
            return false;
        repository.delete(orderLine);
        return true;
    }

    public List<OrderLine> getAllOrderLines(){
        return repository.findAll();
    }

    public OrderLine getOrderLineById(long id){
        return repository.findById(id).orElseThrow();
    }

    public void deleteOrderLineById(long id){
        repository.delete(getOrderLineById(id));
    }

}
