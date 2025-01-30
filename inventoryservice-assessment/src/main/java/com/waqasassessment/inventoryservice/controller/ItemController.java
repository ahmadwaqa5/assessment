package com.waqasassessment.inventoryservice.controller;

import com.waqasassessment.inventoryservice.model.Item;
import com.waqasassessment.inventoryservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item/api")
public class ItemController {
    // GET /item/api/{id}
    @Autowired
    private ItemService itemService;
    @GetMapping("/{id}")
    public Item getItem(@PathVariable final String id) {
        return itemService.getItemById(id);
    }
    @GetMapping("")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}/existsfororder")
    public boolean existsForOrder(@PathVariable final String id, @RequestParam final int quantity) {
        return itemService.existsForOrder(id, quantity);
    }

    @PostMapping("")
    public Item createItem(@RequestBody final Item item) {
        return itemService.createItem(item);
    }

    @PutMapping
    public Item updateItem(@RequestBody final Item item) {
        return itemService.updateItem(item);
    }

    @PutMapping("/{id}/updatequantityafterorder")
    public void updateQuantityAfterOrder(@PathVariable final String id, @RequestParam final int quantity) {
         itemService.updateQuantityAfterOrder(id, quantity);
    }

}
