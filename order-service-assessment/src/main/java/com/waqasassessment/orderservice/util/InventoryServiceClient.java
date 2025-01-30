package com.waqasassessment.orderservice.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "http://localhost:8081")
public interface InventoryServiceClient {
    @GetMapping("/item/api/{id}/existsfororder")
    boolean existsForOrder(@PathVariable final String id, @RequestParam final int quantity);
    @PutMapping("/item/api/{id}/updatequantityafterorder")
    void updateQuantityAfterOrder(@PathVariable final String id, @RequestParam final int quantity);
}