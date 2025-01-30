package com.waqasassessment.orderservice;

import com.waqasassessment.orderservice.exception.ErrorType;
import com.waqasassessment.orderservice.exception.OrderException;
import com.waqasassessment.orderservice.model.OrderEntity;
import com.waqasassessment.orderservice.repository.OrderRepository;
import com.waqasassessment.orderservice.service.OrderService;
import com.waqasassessment.orderservice.util.InventoryServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private InventoryServiceClient inventoryServiceClient;

    @InjectMocks
    private OrderService orderService;

    private OrderEntity order;

    @BeforeEach
    public void setUp() {
        order = new OrderEntity();
        order.setId(1L);
        order.setItemId(101L);
        order.setQuantity(5);
    }

    @Test
    public void testCreateOrder_Success() {
        // Arrange
        when(inventoryServiceClient.existsForOrder(anyString(), anyInt())).thenReturn(true);
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        OrderEntity result = orderService.createOrder(order);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(101L, result.getItemId());
        assertEquals(5, result.getQuantity());

        verify(inventoryServiceClient, times(1)).existsForOrder("101", 5);
        verify(inventoryServiceClient, times(1)).updateQuantityAfterOrder("101", 5);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testCreateOrder_ItemNotInInventory() {
        // Arrange
        when(inventoryServiceClient.existsForOrder(anyString(), anyInt())).thenReturn(false);

        // Act & Assert
        OrderException exception = assertThrows(OrderException.class, () -> {
            orderService.createOrder(order);
        });
        assertEquals(ErrorType.VALIDATION_FAILURE, exception.getErrorType());
        assertEquals("Item with this quantity does not exist in inventory", exception.getMessage());

        verify(inventoryServiceClient, times(1)).existsForOrder("101", 5);
        verify(inventoryServiceClient, never()).updateQuantityAfterOrder(anyString(), anyInt());
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void testGetOrderById_Success() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act
        OrderEntity result = orderService.getOrderById("1");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(101L, result.getItemId());
        assertEquals(5, result.getQuantity());

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetOrderById_NotFound() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        OrderEntity result = orderService.getOrderById("1");

        // Assert
        assertNull(result);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetOrderById_InvalidId() {
        // Act
        OrderEntity result = orderService.getOrderById("-1");

        // Assert
        assertNull(result);
        verify(orderRepository, never()).findById(anyLong());
    }

    @Test
    public void testGetAllOrders() {
        // Arrange
        OrderEntity order1 = new OrderEntity();
        order1.setId(1L);
        OrderEntity order2 = new OrderEntity();
        order2.setId(2L);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        // Act
        List<OrderEntity> result = orderService.getAllOrders();

        // Assert
        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAll();
    }
}