package com.waqasassessment.inventoryservice.service;

import com.waqasassessment.inventoryservice.exception.ErrorType;
import com.waqasassessment.inventoryservice.exception.InventoryException;
import com.waqasassessment.inventoryservice.model.Item;
import com.waqasassessment.inventoryservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item getItemById(final String id) {
        if(StringUtils.isEmpty(id)) {
            throw new InventoryException(ErrorType.VALIDATION_FAILURE, "Item id cannot be empty");
        }
        final  Item item = itemRepository.findById(Long.valueOf(id)).orElse(null);
        if(item == null) {
            throw new InventoryException(ErrorType.NOT_FOUND, "Item not found in the system");
        }
        return item;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public boolean existsForOrder(String id, int quantity) {
        if(StringUtils.isEmpty(id)) {
            throw new InventoryException(ErrorType.VALIDATION_FAILURE, "Item id cannot be empty");
        }
        final Item item = itemRepository.findById(Long.valueOf(id)).orElse(null);
        if(item == null) {
            return false;
        }
        return item.getQuantity() >= quantity;
    }

    public Item createItem(final Item item) {
        if(item == null) {
            throw new InventoryException(ErrorType.VALIDATION_FAILURE, "Item cannot be null");
        }
        return itemRepository.save(item);
    }

    public Item updateItem(final Item item) {
        if(item == null) {
            throw new InventoryException(ErrorType.VALIDATION_FAILURE, "Item cannot be null");
        }
        return itemRepository.save(item);
    }

    public void updateQuantityAfterOrder(final String id, final int quantity) {
        log.info("Updating item quantity after order id: {}, quantity: {}", id, quantity);
        if(StringUtils.isEmpty(id)) {
            throw new InventoryException(ErrorType.VALIDATION_FAILURE, "Item id cannot be empty");
        }
        final Item item = itemRepository.findById(Long.valueOf(id)).orElse(null);
        if(item == null) {
            log.error("Item not found in the system");
            throw new InventoryException(ErrorType.NOT_FOUND, "Item  not found in the system");
        }
        item.setQuantity(item.getQuantity() - quantity);
        itemRepository.save(item);
        log.info("Item quantity updated successfully");
    }
}
