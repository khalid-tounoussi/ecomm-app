package com.dosi.orderservice.repositories;

import com.dosi.orderservice.entities.ProductItem;
import com.dosi.orderservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
