package com.dosi.orderservice.services;

import com.dosi.orderservice.models.Customer;
import com.dosi.orderservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient( name = "inventory-service")
public interface InventoryRestClientService {
    @GetMapping("/products/{id}?projection=fullProduct")
    Product productById(@PathVariable Long id);

    @GetMapping("/products?projection=fullProduct")
    PagedModel<Product> allProducts();
}
