package com;

import com.entity.Order;
import com.entity.Product;
import com.repositories.OrderRepository;
import com.repositories.ProductRepository;
import com.services.OrderService;
import com.services.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository repository;


    @Test
    public void testAdd(){
        Product product = ProductRepositoryTest.createProduct();
        boolean created = service.addProduct(product);
        Assert.assertTrue(created);
        Mockito.verify(repository, Mockito.times(1)).save(product);
        Mockito.verify(repository, Mockito.times(1)).existsById(product.getId());


        Mockito.when(repository.existsById(product.getId())).thenReturn(true);
        boolean updated = service.updateProduct(product);
        Mockito.verify(repository, Mockito.times(2)).existsById(product.getId());
        Mockito.verify(repository, Mockito.times(2)).save(product);
        Assert.assertTrue(updated);

        List<Product> list = service.getAllProducts();
        Mockito.verify(repository, Mockito.times(1)).findAll();

        boolean deleted = service.deleteProduct(product);
        Mockito.verify(repository, Mockito.times(3)).existsById(product.getId());
        Mockito.verify(repository, Mockito.times(1)).delete(product);
        Assert.assertTrue(deleted);
    }
}
