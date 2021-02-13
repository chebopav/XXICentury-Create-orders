package com;

import com.entity.Order;
import com.repositories.OrderRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@DataJpaTest
@RunWith(SpringRunner.class)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Test
    public void test(){
        Order order = createOrder();
        repository.save(order);
        Order findOne = repository.getOne(order.getId());
        Assert.assertEquals(order, findOne);

        Assert.assertEquals(1, repository.findAll().size());

        order.setAddress("new address");
        repository.save(order);
        Assert.assertEquals(1, repository.findAll().size());
        Assert.assertEquals("new address", repository.getOne(order.getId()).getAddress());

        repository.delete(order);
        Assert.assertEquals(0, repository.findAll().size());
    }


    protected static Order createOrder(){
        Order order = new Order();
        order.setClientName("ClientName");
        order.setDate(LocalDate.now().plus(10, ChronoUnit.DAYS));
        order.setAddress("address");
        return order;
    }
}
