package com;

import com.entity.Order;
import com.entity.OrderLine;
import com.entity.Product;
import com.repositories.OrderLineRepository;
import com.services.OrderLineService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderLineServiceTest {

    @Autowired
    private OrderLineService service;

    @MockBean
    private OrderLineRepository repository;

    @Test
    public void test(){
        OrderLine orderLine = createOrderLine();
        boolean created = service.addOrderLine(orderLine);
        Assert.assertTrue(created);
        Mockito.verify(repository, Mockito.times(1)).save(orderLine);
        Mockito.verify(repository, Mockito.times(1)).existsById(orderLine.getId());

        Mockito.when(repository.existsById(orderLine.getId())).thenReturn(true);

        boolean updated = service.updateOrderLine(orderLine);
        Mockito.verify(repository, Mockito.times(2)).existsById(orderLine.getId());
        Mockito.verify(repository, Mockito.times(2)).save(orderLine);
        Assert.assertTrue(updated);

        List<OrderLine> list = service.getAllOrderLines();
        Mockito.verify(repository, Mockito.times(1)).findAll();

        boolean deleted = service.deleteOrderLine(orderLine);
        Mockito.verify(repository, Mockito.times(3)).existsById(orderLine.getId());
        Mockito.verify(repository, Mockito.times(1)).delete(orderLine);
        Assert.assertTrue(deleted);
    }

    protected static OrderLine createOrderLine(){
        Order order = OrderRepositoryTest.createOrder();
        Product product = ProductRepositoryTest.createProduct();
        OrderLine orderLine = new OrderLine();
        orderLine.setOrder(order);
        orderLine.setProduct(product);
        orderLine.setCount(10);
        return orderLine;
    }

}
