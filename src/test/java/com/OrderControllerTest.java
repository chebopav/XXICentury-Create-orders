package com;

import com.entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.OrderService;
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
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderService service;

    @Test
    public void test() throws Exception {
        Order order = OrderRepositoryTest.createOrder();
        order.setId(1L);
        List<Order> orders = new ArrayList<>(1);
        orders.add(order);
        Mockito.when(service.addOrder(Mockito.any())).thenReturn(true);
        Mockito.when(service.getOrderById(1)).thenReturn(order);
        Mockito.when(service.getAllOrders()).thenReturn(orders);

        mockMvc.perform(
                post("/order")
                        .content(mapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(order))
                );

        mockMvc.perform(
                get("/order/1")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.clientName").value("ClientName"))
                .andExpect(jsonPath("$.address").value("address")
                );

        mockMvc.perform(
                get("/order")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orders))
                );

        Order newOrder = OrderRepositoryTest.createOrder();
        newOrder.setAddress("New Address");
        mockMvc.perform(
                put("/order/1")
                        .content(mapper.writeValueAsString(newOrder))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.clientName").value("ClientName"))
                .andExpect(jsonPath("$.address").value("New Address")
                );

        mockMvc.perform(
                delete("/order/1")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(order))
                );
    }
}
