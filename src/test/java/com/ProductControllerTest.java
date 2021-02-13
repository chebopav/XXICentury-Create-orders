package com;

import com.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService service;

    @Test
    public void test() throws Exception {
        Product product = ProductRepositoryTest.createProduct();
        product.setId(1L);
        List<Product> products = new ArrayList<>(1);
        products.add(product);
        Mockito.when(service.addProduct(Mockito.any())).thenReturn(true);
        Mockito.when(service.getProductById(1)).thenReturn(product);
        Mockito.when(service.getAllProducts()).thenReturn(products);

        mockMvc.perform(
                post("/product")
                    .content(mapper.writeValueAsString(product))
                    .contentType(MediaType.APPLICATION_JSON)
                )
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(product))
                );

        mockMvc.perform(
                get("/product/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product Name"))
                .andExpect(jsonPath("$.price").value(100.1)
                );

        mockMvc.perform(
                get("/product")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(products))
                );

        Product newProduct = ProductRepositoryTest.createProduct();
        newProduct.setPrice(1000.1f);
        mockMvc.perform(
                put("/product/1")
                        .content(mapper.writeValueAsString(newProduct))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product Name"))
                .andExpect(jsonPath("$.price").value(1000.1)
                );

        mockMvc.perform(
                delete("/product/1")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(product))
                );
    }
}
