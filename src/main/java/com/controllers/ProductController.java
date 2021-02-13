package com.controllers;

import com.entity.Product;
import com.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAllGoods() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getGoodById(@PathVariable long id) {
        return service.getProductById(id);
    }

    @PostMapping
    public Product addGood(@RequestBody Product product) {
        service.addProduct(product);
        return product;
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable long id, @RequestBody Product newProduct) {
        Product product = service.getProductById(id);
        product.setName(newProduct.getName());
        product.setPrice(newProduct.getPrice());
        service.updateProduct(product);
        return product;
    }

    @DeleteMapping("/{id}")
    public Product deleteGood(@PathVariable long id) {
        Product product = service.getProductById(id);
        service.deleteProduct(product);
        return product;
    }
}
