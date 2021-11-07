package com.aman.checkoutsystem.domain.product;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Double price;

    @NonNull
    private String currency;

    @NonNull
    private String description;

    private Long quantity;

    private double discount;

    private Long freeItemId;

    private Long discountProductId;

    private DiscountType discountType;

}