package com;

import com.entity.OrderLine;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.OrderLineService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderLineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderLineService service;

    @Test
    public void test() throws Exception {
        OrderLine orderLine = OrderLineServiceTest.createOrderLine();
        orderLine.setId(1);
        List<OrderLine> orderLines = new ArrayList<>(1);
        orderLines.add(orderLine);

        Mockito.when(service.addOrderLine(Mockito.any())).thenReturn(true);
        Mockito.when(service.getOrderLineById(1)).thenReturn(orderLine);
        Mockito.when(service.getAllOrderLines()).thenReturn(orderLines);

        mockMvc.perform(
                get("/order_line/1")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.count").value(10)
                );

        mockMvc.perform(
                get("/order_line")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderLines))
                );

        mockMvc.perform(
                delete("/order_line/1")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderLine))
                );
    }
}
