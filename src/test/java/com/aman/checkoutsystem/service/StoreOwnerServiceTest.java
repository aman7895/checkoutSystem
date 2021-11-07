package com.aman.checkoutsystem.service;

import com.aman.checkoutsystem.domain.product.Product;
import com.aman.checkoutsystem.infrastructure.repository.ProductRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class StoreOwnerServiceTest {

    Product p1;
    Product p2;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    StoreOwnerService storeOwnerService;

    @BeforeEach
    public void setUpProductsHereToUse() {
        p1 = new Product();
        p1.setId(1L);
        p1.setName("Samsung");
        p1.setPrice(100.0);
        p1.setCurrency("HKD");
        p1.setDescription("New Phone");
        p1.setFreeItemId(0L);
        p1.setQuantity(100L);

        p2 = new Product();
        p2.setId(2L);
        p2.setName("Samsung");
        p2.setPrice(200.0);
        p2.setCurrency("HKD");
        p2.setDescription("New Phone");
        p2.setFreeItemId(0L);
        p2.setQuantity(100L);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkIfTheDBFindsAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
        List<Product> products = storeOwnerService.getProducts();
        verify(productRepository, times(1)).findAll();
        assertEquals(2, products.size());
        assertTrue(products.contains(p1));
    }

    @Test
    public void checkIfTheDBHasNoProducts() {
        when(productRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        assertEquals(Collections.EMPTY_LIST, storeOwnerService.getProducts());
    }

    @Test
    public void checkIfTheDBWasCalledToSaveTheProductAndCreateProduct() {
        when(storeOwnerService.createNewProduct(p1)).thenReturn(p1);
        storeOwnerService.createNewProduct(p1);
        verify(productRepository, times(1)).save(p1);
        Product product = storeOwnerService.createNewProduct(p1);
        assertEquals(p1, product);
    }

    @Test
    public void checkIfTheDeleteProductFromDBCallIsInvoked() {
        Long id = p1.getId();
        doNothing().when(productRepository).deleteById(id);
        storeOwnerService.removeProduct(id);
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    public void checkIfDBSaveIsNotInvokedIfProductIsNotFound() {
        Long id = p1.getId();
        when(productRepository.findById(id)).thenReturn(null);
        assertEquals("Product not found", storeOwnerService.updateProduct(p1, id));
    }

    @Test
    public void checkDBFindProductByIdIsInvokedToUpdateProduct() {
        Long id = p1.getId();
        when(productRepository.findById(id)).thenReturn(Optional.ofNullable(p1));
        String updated = storeOwnerService.updateProduct(p1, id);
        assertEquals("Product saved: " + Optional.of(p1), updated);

    }
}