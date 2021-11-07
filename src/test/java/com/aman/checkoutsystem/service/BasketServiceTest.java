package com.aman.checkoutsystem.service;

import com.aman.checkoutsystem.domain.basket.Basket;
import com.aman.checkoutsystem.domain.product.Product;
import com.aman.checkoutsystem.infrastructure.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BasketServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    BasketService basketService;

    Basket basket;
    String id;
    Product p1;
    Product p2;
    Long quantity;

    @BeforeEach
    public void setUp() {
        id = "basket-1";

        p1 = new Product();
        p1.setId(1L);
        p1.setQuantity(1L);
        p1.setFreeItemId(0L);

        p2 = new Product();
        p2.setId(2L);
        p2.setQuantity(1L);
        p2.setFreeItemId(1L);

        quantity = 1L;

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getNullIfTheRightBasketMapIsNotUsed() {
        basket = new Basket();
        assertNotEquals(basket, basketService.getBasketId(id));
    }

    @Test
    public void getBackTheBasketIdentifier() {
        basket = new Basket();
        basketService.getBasketHashMap().put(id, basket);
        assertEquals(basket, basketService.getBasketId(id));
    }

    @Test
    public void createANewIdForEveryBasketAndCheckItsNotRepeated() {
        basket = new Basket();
        basketService.getBasketHashMap().put(id, basket);
        assertNotEquals(id, basketService.createBasket(basket));
    }

    @Test
    public void checkIfProductIsNotAddedToBasketIfItDoesntExistThrowsTheRightException() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> basketService.addProductToBasket(id, p1.getId(), quantity),
                "Basket ID doesn't exist: " + id
        );
        assertTrue(thrown.getMessage().equals("Basket ID doesn't exist: " + id));
    }

    @Test
    public void checkIfProductBeingAddedToBasketDoesntExistThrowsTheRightException() {
        basket = new Basket();
        basketService.getBasketHashMap().put(id, basket);
        Exception thrown = assertThrows(
                Exception.class,
                () -> basketService.addProductToBasket(id, p1.getId(), quantity),
                "Product ID doesn't exist: " + p1.getId()
        );
        assertTrue(thrown.getMessage().equals("Product ID doesn't exist: " + p1.getId()));
    }

    @Test
    public void checkIfProductToBeAddedQuantityIsGreaterThanTotalProductQuantityThrowsTheRightException() {
        basket = new Basket();
        basketService.getBasketHashMap().put(id, basket);
        when(productRepository.findById(p1.getId())).thenReturn(java.util.Optional.ofNullable(p1));
        when(productRepository.findById(p1.getId())).thenReturn(java.util.Optional.ofNullable(p1));

        Exception thrown = assertThrows(
                Exception.class,
                () -> basketService.addProductToBasket(id, p1.getId(), 2L),
                "Not enough quantity for product ID: " + p1.getId()
        );
        assertTrue(thrown.getMessage().equals("Not enough quantity for product ID: " + p1.getId()));
    }

    @Test
    public void checkIfProductCanBeBoughtInTheDesiredQuantity() throws Exception {
        basket = new Basket();
        basketService.getBasketHashMap().put(id, basket);
        when(productRepository.findById(p1.getId())).thenReturn(java.util.Optional.ofNullable(p1));
        when(productRepository.findById(p1.getId())).thenReturn(java.util.Optional.ofNullable(p1));
        when(productRepository.save(p1)).thenReturn(p1);
        basketService.addProductToBasket(id, p1.getId(), quantity);
        verify(productRepository, times(1)).save(p1);
    }

    @Test
    public void checkIfProductHasAFreeItemIdAndItsBeingAdded() throws Exception {
        basket = new Basket();
        basketService.getBasketHashMap().put(id, basket);
        when(productRepository.findById(p2.getId())).thenReturn(java.util.Optional.ofNullable(p2));
        when(productRepository.findById(p2.getId())).thenReturn(java.util.Optional.ofNullable(p2));
        when(productRepository.save(p2)).thenReturn(p2);
        when(productRepository.findById(p2.getFreeItemId())).thenReturn(java.util.Optional.ofNullable(p1));
        when(productRepository.save(p1)).thenReturn(p1);
        basketService.addProductToBasket(id, p2.getId(), quantity);
        verify(productRepository, times(1)).save(p2);
        verify(productRepository, times(1)).save(p1);
    }


}
