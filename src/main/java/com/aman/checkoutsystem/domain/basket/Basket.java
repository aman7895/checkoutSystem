package com.aman.checkoutsystem.domain.basket;

import com.aman.checkoutsystem.domain.product.DiscountType;
import com.aman.checkoutsystem.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basket {

    private Map<Long, Long> productQuantity = new HashMap<>();
    private Map<Long, Product> productMap = new HashMap<>();
    private Map<Long, Product> freeItem = new HashMap<>();
    private Map<Long, Long> freeItemQuantity = new HashMap<>();
    private Map<Long, Double> discount = new HashMap<>();
    private Map<Long, Long> discQuantity = new HashMap<>();

    private double cost;

    public void addProduct(Product product, Long quantity) {
        productQuantity.put(
                product.getId(),
                productQuantity.getOrDefault(product.getId(), 0L) + quantity);
        productMap.putIfAbsent(product.getId(), product);
    }

    // Adding products to a map
    public double getCost() {
        productMap.forEach((key, product) -> {
            // Checking is the deal type is valid
            if (product.getDiscountType() != null && product.getDiscountType().equals(DiscountType.BuyXGetYOnSecond)) {
                discount.putIfAbsent(product.getDiscountProductId(), product.getDiscount());
                discQuantity.put(product.getDiscountProductId(),
                        discQuantity.getOrDefault(product.getDiscountProductId(), 0L) + productQuantity.get(key));
            }
        });

        double cost = 0.0;
        Long id;
        Product product;

        // Looping through products
        for (Map.Entry<Long, Product> itr : productMap.entrySet()) {
            id = itr.getKey();
            product = itr.getValue();
            double discountQuantity = 0;
            // If there is a discount product linked to the selected product
            if (discount.containsKey(id)) {
                // If the discount is on the product itself
                if(product.getDiscountProductId() == id) {
                    discountQuantity = Math.floor(productQuantity.get(id) / 2);
                }
                else {
                    discountQuantity = productQuantity.get(id).doubleValue();
                }
                cost += product.getPrice() * (1 - (discount.get(id) / 100)) * Math.min(discQuantity.get(id), discountQuantity);
                cost += product.getPrice() * Math.max(0, productQuantity.get(id) - Math.min(discQuantity.get(id), discountQuantity));
            } else {
                cost += product.getPrice() * productQuantity.get(id);
            }
        }
        return cost;
    }

    public void addFreeItem(Product product, Long Quantity) {
        freeItem.putIfAbsent(product.getId(), product);
        freeItemQuantity.put(product.getId(), freeItemQuantity.getOrDefault(product.getId(), 0L) + Quantity);
    }

}