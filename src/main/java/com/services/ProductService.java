package com.services;

import com.entity.Product;
import com.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public boolean addProduct(Product product) {
        if (product == null || repository.existsById(product.getId()))
            return false;
        repository.save(product);
        return true;
    }

    public boolean updateProduct(Product product){
        if (!repository.existsById(product.getId()))
            return false;
        repository.save(product);
        return true;
    }

    public boolean deleteProduct(Product product){
        if (!repository.existsById(product.getId()))
            return false;
        repository.delete(product);
        return true;
    }

    public List<Product> getAllProducts(){
        return repository.findAll();
    }

    public Product getProductById(long id){
        return repository.findById(id).orElseThrow();
    }

    public void deleteProductById(long id){
        repository.delete(getProductById(id));
    }



}
