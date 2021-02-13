package com;

import com.entity.Order;
import com.repositories.OrderRepository;
import com.services.OrderService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService service;

    @MockBean
    private OrderRepository repository;


    @Test
    public void testAdd(){
        Order order = OrderRepositoryTest.createOrder();
        boolean created = service.addOrder(order);
        Assert.assertTrue(created);
        Mockito.verify(repository, Mockito.times(1)).save(order);
        Mockito.verify(repository, Mockito.times(1)).existsById(order.getId());

        Mockito.when(repository.existsById(order.getId())).thenReturn(true);
        boolean updated = service.updateOrder(order);
        Mockito.verify(repository, Mockito.times(2)).existsById(order.getId());
        Mockito.verify(repository, Mockito.times(2)).save(order);
        Assert.assertTrue(updated);

        List<Order> list = service.getAllOrders();
        Mockito.verify(repository, Mockito.times(1)).findAll();

        boolean deleted = service.deleteOrder(order);
        Mockito.verify(repository, Mockito.times(3)).existsById(order.getId());
        Mockito.verify(repository, Mockito.times(1)).delete(order);
        Assert.assertTrue(deleted);
    }

}
