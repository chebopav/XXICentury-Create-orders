package com;

import com.entity.Order;
import com.entity.OrderLine;
import com.entity.Product;
import com.repositories.OrderLineRepository;
import com.repositories.OrderRepository;
import com.repositories.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class OrderLineRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderLineRepository repository;

    @Test
    public void test(){
        OrderLine orderLine = createOrderLine();
        repository.save(orderLine);
        OrderLine findOne = repository.getOne(orderLine.getId());
        Assert.assertEquals(orderLine, findOne);

        Assert.assertEquals(1, repository.findAll().size());

        orderLine.setCount(50);
        repository.save(orderLine);
        Assert.assertEquals(1, repository.findAll().size());
        Assert.assertEquals(50, repository.getOne(orderLine.getId()).getCount());

        repository.delete(orderLine);
        Assert.assertEquals(0, repository.findAll().size());
    }


    private OrderLine createOrderLine(){
        Order order = OrderRepositoryTest.createOrder();
        Product product = ProductRepositoryTest.createProduct();
        productRepository.save(product);
        orderRepository.save(order);

        OrderLine orderLine = new OrderLine();
        orderLine.setOrder(order);
        orderLine.setProduct(product);
        orderLine.setCount(10);
        return orderLine;
    }
}
