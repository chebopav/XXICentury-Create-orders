package com.controllers;

import com.entity.Order;
import com.entity.OrderLine;
import com.entity.Product;
import com.services.OrderLineService;
import com.services.OrderService;
import com.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order_line")
public class OrderLineController {
    private OrderLineService service;
    private OrderService orderService;
    private ProductService productService;

    @Autowired
    public OrderLineController(OrderLineService service, OrderService orderService, ProductService productService) {
        this.service = service;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderLine> getAllOrderLines(){
        return service.getAllOrderLines();
    }

    @GetMapping("/{id}")
    public OrderLine getOrderLine(@PathVariable long id){
        return service.getOrderLineById(id);
    }

    @PostMapping("/")
    public OrderLine makeOrder(@RequestParam long order_id, @RequestParam long product_id, @RequestParam int count){
        OrderLine orderLine = new OrderLine();
        Order order = orderService.getOrderById(order_id);
        Product product = productService.getProductById(product_id);
        orderLine.setOrder(order);
        order.getOrderLines().add(orderLine);
        orderLine.setProduct(product);
        product.getLines().add(orderLine);
        orderLine.setCount(count);

        service.addOrderLine(orderLine);
        orderService.updateOrder(order);
        productService.updateProduct(product);
        return orderLine;
    }

    @DeleteMapping("/{id}")
    public OrderLine delete(@PathVariable long id){
        OrderLine orderLine = service.getOrderLineById(id);
        service.deleteOrderLine(orderLine);
        return orderLine;
    }
}
