package com.aman.checkoutsystem.service;

import com.aman.checkoutsystem.domain.product.Product;
import com.aman.checkoutsystem.infrastructure.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
This service is implemented for the store owner. It helps to get all the current products,
create a new product, update a product and remove a product. It also gives the store
owner the choice of adding a free item to any product or giving a discount in the form of
Buy X get Y % discount on Z. Here the X and Z can be same as well, while the Y is the
range of 0-100 percent discount. The discount item id, discount percentage can be stored
with the product along with a Discount Type to later confirm that the discount applies.
 */

@Service
@Slf4j
public class StoreOwnerService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product createNewProduct(Product product) {
        return productRepository.save(product);
    }

    public String updateProduct(Product product, Long id) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct == null) {
            return "Product not found";
        } else {
            if (product.getCurrency() != null) {
                existingProduct.get().setCurrency(product.getCurrency());
            }

            if (product.getDescription() != null) {
                existingProduct.get().setDescription(product.getDescription());
            }

            if (product.getName() != null) {
                existingProduct.get().setName(product.getName());
            }

            if (product.getPrice() != null) {
                existingProduct.get().setPrice(product.getPrice());
            }

            if (product.getFreeItemId() != null) {
                existingProduct.get().setFreeItemId(product.getFreeItemId());
            }

            if (product.getQuantity() != 0) {
                existingProduct.get().setQuantity(product.getQuantity());
            }

            if (product.getDiscount() != 0) {
                existingProduct.get().setDiscount(product.getDiscount());
            }

            if (product.getDiscountType() != null) {
                existingProduct.get().setDiscountType(product.getDiscountType());
            }

            if (product.getDiscountProductId() != null) {
                existingProduct.get().setDiscountProductId(product.getDiscountProductId());
            }

            productRepository.save(existingProduct.get());

            return "Product saved: " + existingProduct;

        }
    }

    public void removeProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Product not found with exception: " + e.getMessage());
        }
    }

}