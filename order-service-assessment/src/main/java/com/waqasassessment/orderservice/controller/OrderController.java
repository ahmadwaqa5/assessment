package com.waqasassessment.orderservice.controller;

import com.waqasassessment.orderservice.model.OrderEntity;
import com.waqasassessment.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order/api")
public class OrderController {
   @Autowired
   private OrderService orderService;

    @GetMapping("/{id}")
    public OrderEntity getOrder(@PathVariable final String id) {
        return orderService.getOrderById(id);
    }
    @GetMapping("")
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }
    @PostMapping
    public OrderEntity createOrder(@RequestBody OrderEntity order) {
        return orderService.createOrder(order);
    }

}
