package com.waqasassessment.inventoryservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.waqasassessment.inventoryservice.exception.ErrorType;
import com.waqasassessment.inventoryservice.exception.InventoryException;
import com.waqasassessment.inventoryservice.model.Item;
import com.waqasassessment.inventoryservice.repository.ItemRepository;
import com.waqasassessment.inventoryservice.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetItemById_Success() {
        // Arrange
        Item mockItem = new Item();
        mockItem.setId(1L);
        mockItem.setName("Test Item");
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));

        // Act
        Item result = itemService.getItemById("1");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Item", result.getName());
    }

    @Test
    public void testGetItemById_NotFound() {
        // Arrange
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        final InventoryException exception = assertThrows(InventoryException.class, () -> {
            itemService.getItemById("1");
        });
        assertEquals(ErrorType.NOT_FOUND, exception.getErrorType());
    }

    @Test
    public void testGetItemById_ValidationFailure() {
        // Act & Assert
        final InventoryException exception = assertThrows(InventoryException.class, () -> {
            itemService.getItemById("");
        });
        assertEquals(ErrorType.VALIDATION_FAILURE, exception.getErrorType());
    }

    @Test
    public void testGetAllItems() {
        final Item item1 = new Item();
        item1.setId(1L);
        final Item item2 = new Item();
        item2.setId(2L);
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        final List<Item> result = itemService.getAllItems();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testExistsForOrder_Success() {
        final Item mockItem = new Item();
        mockItem.setId(1L);
        mockItem.setQuantity(10);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));

        boolean result = itemService.existsForOrder("1", 5);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testExistsForOrder_ItemNotFound() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        final boolean result = itemService.existsForOrder("1", 5);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testExistsForOrder_ValidationFailure() {
        final InventoryException exception = assertThrows(InventoryException.class, () -> {
            itemService.existsForOrder("", 5);
        });
        assertEquals(ErrorType.VALIDATION_FAILURE, exception.getErrorType());
    }

    @Test
    public void testCreateItem_Success() {
        final Item newItem = new Item();
        newItem.setId(1L);
        newItem.setName("New Item");
        when(itemRepository.save(newItem)).thenReturn(newItem);

        final Item result = itemService.createItem(newItem);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Item", result.getName());
    }

    @Test
    public void testCreateItem_ValidationFailure() {
        final InventoryException exception = assertThrows(InventoryException.class, () -> {
            itemService.createItem(null);
        });
        assertEquals(ErrorType.VALIDATION_FAILURE, exception.getErrorType());
    }

    @Test
    public void testUpdateItem_Success() {
        final Item existingItem = new Item();
        existingItem.setId(1L);
        existingItem.setName("Existing Item");
        when(itemRepository.save(existingItem)).thenReturn(existingItem);

        final Item result = itemService.updateItem(existingItem);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Existing Item", result.getName());
    }

    @Test
    public void testUpdateItem_ValidationFailure() {
        final InventoryException exception = assertThrows(InventoryException.class, () -> {
            itemService.updateItem(null);
        });
        assertEquals(ErrorType.VALIDATION_FAILURE, exception.getErrorType());
    }

    @Test
    public void testUpdateQuantityAfterOrder_Success() {
        final Item mockItem = new Item();
        mockItem.setId(1L);
        mockItem.setQuantity(10);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));

        itemService.updateQuantityAfterOrder("1", 3);

        // Assert
        assertEquals(7, mockItem.getQuantity());
        verify(itemRepository, times(1)).save(mockItem);
    }

    @Test
    public void testUpdateQuantityAfterOrder_ItemNotFound() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        InventoryException exception = assertThrows(InventoryException.class, () -> {
            itemService.updateQuantityAfterOrder("1", 3);
        });
        assertEquals(ErrorType.NOT_FOUND, exception.getErrorType());
    }

    @Test
    public void testUpdateQuantityAfterOrder_ValidationFailure() {
        InventoryException exception = assertThrows(InventoryException.class, () -> {
            itemService.updateQuantityAfterOrder("", 3);
        });
        assertEquals(ErrorType.VALIDATION_FAILURE, exception.getErrorType());
    }
}