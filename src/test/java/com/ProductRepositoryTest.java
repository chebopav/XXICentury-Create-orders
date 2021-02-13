package com;

import com.entity.Product;
import com.repositories.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    public void testAddAndGet(){
        Product product = createProduct();
        repository.save(product);
        Product findOne = repository.getOne(product.getId());
        Assert.assertEquals(findOne, product);

        Assert.assertEquals(1, repository.findAll().size());

        product.setPrice(1000f);
        repository.save(product);
        Assert.assertEquals(1, repository.findAll().size());
        Assert.assertEquals(1000f, repository.getOne(product.getId()).getPrice(), 0);

        repository.delete(product);
        Assert.assertEquals(0, repository.findAll().size());
    }

    protected static Product createProduct(){
        Product product = new Product();
        product.setName("Product Name");
        product.setPrice(100.1f);
        return product;
    }
}
