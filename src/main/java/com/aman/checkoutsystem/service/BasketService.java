package com.aman.checkoutsystem.service;

import com.aman.checkoutsystem.domain.basket.Basket;
import com.aman.checkoutsystem.domain.product.Product;
import com.aman.checkoutsystem.infrastructure.repository.ProductRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

/*
This service is implemented for user basket. It maintains a basketHashMap
that contains all the basket. The functions here help to create a new
basket Id, return the basket and add product to the basket. The basket
object does the cost calculation which can be seen when createBasket
method is called.
 */

@Data
@Service
@Slf4j
public class BasketService {

    @Autowired
    ProductRepository productRepository;

    private HashMap<String, Basket> basketHashMap = new HashMap<>();

    public String createBasket(Basket basket) {
        String uniqueID = UUID.randomUUID().toString();
        basketHashMap.put(uniqueID, basket);
        return uniqueID;
    }

    public Basket getBasketId(String basketId) {
        return basketHashMap.get(basketId);
    }

    public void addProductToBasket(String basketId, Long productId, Long quantity) throws Exception {
        if (basketHashMap.containsKey(basketId)) {
            if (productRepository.findById(productId).isPresent()) {
                Product currentProduct = productRepository.findById(productId).get();
                Long productQuantity = currentProduct.getQuantity();
                if (productQuantity >= quantity) {

                    // Reducing product quantity
                    currentProduct.setQuantity(productQuantity - quantity);
                    productRepository.save(currentProduct);

                    // Adding product + quantity to basket
                    Basket currentBasket = basketHashMap.get(basketId);
                    currentBasket.addProduct(currentProduct, quantity);

                    // Adding free item
                    if (currentProduct.getFreeItemId() != 0) {
                        Product freeProduct = productRepository.findById(currentProduct.getFreeItemId()).get();
                        Long freeQuantity = freeProduct.getQuantity();
                        freeProduct.setQuantity(Math.max(0, freeQuantity - quantity));
                        productRepository.save(freeProduct);
                        currentBasket.addFreeItem(freeProduct, Math.min(quantity, freeQuantity));
                    }

                } else {
                    throw new Exception("Not enough quantity for product ID: " + productId);
                }
            } else {
                throw new Exception("Product ID doesn't exist: " + productId);
            }
        } else {
            throw new Exception("Basket ID doesn't exist: " + basketId);
        }
    }

}
