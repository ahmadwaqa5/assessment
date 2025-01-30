package com.waqasassessment.orderservice.service;

import com.waqasassessment.orderservice.exception.ErrorType;
import com.waqasassessment.orderservice.exception.OrderException;
import com.waqasassessment.orderservice.model.OrderEntity;
import com.waqasassessment.orderservice.repository.OrderRepository;
import com.waqasassessment.orderservice.util.InventoryServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InventoryServiceClient inventoryServiceClient;
    public OrderEntity createOrder(final OrderEntity order) {
        // verify if an item is present in the inventory before creating an order
        checkIfOrderItemsExistInInventory(order);
        final OrderEntity orderSaved = orderRepository.save(order);
        updateOrderInInventory(orderSaved);
        return orderSaved;
    }

    private void updateOrderInInventory(final OrderEntity orderSaved) {
        // make http request to another service to update the quantity of the item in the inventory
        // with two params item id and quantity
        log.info("Updating inventory for order id: {}, quantity: {}", orderSaved.getItemId(), orderSaved.getQuantity());
        inventoryServiceClient.updateQuantityAfterOrder(String.valueOf(orderSaved.getItemId()), orderSaved.getQuantity());
        log.info("Inventory updated");
    }

    private void checkIfOrderItemsExistInInventory(final OrderEntity order) {
        // make http request to another service to find out with two params item id and quantity
        // if the item is present in the inventory
        // if not throw an exception
       final boolean quantityExistsForOrder = inventoryServiceClient.existsForOrder(String.valueOf(order.getItemId()), order.getQuantity());
       if(!quantityExistsForOrder) {
           throw new OrderException(ErrorType.VALIDATION_FAILURE, "Item with this quantity does not exist in inventory");
       }
    }

    public OrderEntity getOrderById(final String orderId) {
        final long id = Long.parseLong(orderId);
        if(id < 0)
            return null;
        return orderRepository.findById(id).orElse(null);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
}
