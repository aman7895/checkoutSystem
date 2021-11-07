package com.aman.checkoutsystem.controller.rest;

import com.aman.checkoutsystem.domain.basket.Basket;
import com.aman.checkoutsystem.service.BasketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
This is a controller used by the user to work on their basket.
It connects to the BasketService where the logic is implemented.
 */

@RestController
@Slf4j
public class BasketController {

    @Autowired
    BasketService basketService;

    @GetMapping("/basket/{basketId}")
    private Basket getBasket(@PathVariable("basketId") String basketId) {
        return basketService.getBasketId(basketId);
    }

    @PostMapping("/basket/createBasket")
    private String createBasket(@RequestBody Basket basket) {
        return basketService.createBasket(basket);
    }

    @PutMapping("/basket/addProduct/{basketId}/{productId}/{quantity}")
    private void addProductToBasket(@PathVariable("basketId") String basketId,
                                    @PathVariable("productId") Long productId,
                                    @PathVariable("quantity") Long quantity) {

        try {
            basketService.addProductToBasket(basketId, productId, quantity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}