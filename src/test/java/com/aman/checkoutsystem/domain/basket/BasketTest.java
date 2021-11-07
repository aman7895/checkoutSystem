package com.aman.checkoutsystem.domain.basket;

import com.aman.checkoutsystem.domain.product.DiscountType;
import com.aman.checkoutsystem.domain.product.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {

    Basket basket;
    Product p1;
    Product p2;
    Product p3;
    Product p4;

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
        p2.setName("Galaxy");
        p2.setPrice(200.0);
        p2.setCurrency("HKD");
        p2.setDescription("New Watch");
        p2.setFreeItemId(0L);
        p2.setQuantity(100L);

        p3 = new Product();
        p3.setId(3L);
        p3.setName("Nexus");
        p3.setPrice(200.0);
        p3.setCurrency("HKD");
        p3.setDescription("New Tablet");
        p3.setFreeItemId(0L);
        p3.setQuantity(100L);
        p3.setDiscountType(DiscountType.BuyXGetYOnSecond);
        p3.setDiscountProductId(3L);
        p3.setDiscount(20);

        p4 = new Product();
        p4.setId(4L);
        p4.setName("Ace");
        p4.setPrice(100.0);
        p4.setCurrency("HKD");
        p4.setDescription("New Mobile");
        p4.setFreeItemId(0L);
        p4.setQuantity(100L);
        p4.setDiscountType(DiscountType.BuyXGetYOnSecond);
        p4.setDiscountProductId(2L);
        p4.setDiscount(50);

    }

    @Test
    public void addProductsToBasketAndCheckProductsInBasket() {
        basket = new Basket();
        basket.addProduct(p1, 1L);
        assertTrue(basket.getProductMap().containsKey(p1.getId()));
        assertFalse(basket.getProductMap().containsKey(p2.getId()));
        assertTrue(basket.getProductMap().containsValue(p1));
        assertFalse(basket.getProductMap().containsValue(p2));
    }

    @Test
    public void addProductsToBasketAndCheckProductQuantity() {
        basket = new Basket();
        basket.addProduct(p1, 5L);
        assertTrue(basket.getProductQuantity().containsValue(5L));
        assertFalse(basket.getProductQuantity().containsValue(p1.getQuantity()));
    }

    @Test
    public void addProductsToBasketAndCheckForCountOfEachProduct() {
        basket = new Basket();
        basket.addProduct(p1, 5L);
        basket.addProduct(p2, 3L);

        assertEquals(5L, basket.getProductQuantity().get(p1.getId()));
        assertEquals(3L, basket.getProductQuantity().get(p2.getId()));

        assertNotEquals(5L, basket.getProductQuantity().get(p2.getId()));
    }

    @Test
    public void addProductsToBasketAndGetTotalNumberOfProductsInBasket() {
        basket = new Basket();
        Long numberOfProducts = 0L;
        basket.addProduct(p1, 5L);
        basket.addProduct(p2, 5L);
        for (Long quantity : basket.getProductQuantity().values()) {
            numberOfProducts += quantity;
        }
        assertEquals(10L, (long) numberOfProducts);
    }

    @Test
    public void addProductsToBasketAndGetCost() {
        basket = new Basket();
        basket.addProduct(p1, 1L);
        basket.addProduct(p2, 1L);
        assertEquals(300.0, basket.getCost());
    }

    @Test
    public void addProductAndCheckIfTheGivenDiscountIsAppliedOnTheSameSecond() {
        basket = new Basket();
        basket.addProduct(p3, 2L);
        assertEquals(360.0, basket.getCost());
        basket.addProduct(p3, 2L);
        assertEquals(720.0, basket.getCost());
    }

    @Test
    public void addProductAndCheckIfTheGivenDiscountIsAppliedOnADifferentSecond() {
        basket = new Basket();
        basket.addProduct(p4, 1L);
        basket.addProduct(p2, 1L);
        assertEquals(200.0, basket.getCost());
    }

    @Test
    public void addFreeProductToBasketAndCheckIfItsPresent() {
        basket = new Basket();
        basket.addFreeItem(p1, 1L);
        assertTrue(basket.getFreeItem().containsKey(p1.getId()));
        assertFalse(basket.getFreeItem().containsKey(p2.getId()));
    }


}