package com.aman.checkoutsystem.infrastructure.repository;

import com.aman.checkoutsystem.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}