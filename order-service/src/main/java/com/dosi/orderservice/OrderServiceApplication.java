package com.dosi.orderservice;

import com.dosi.orderservice.entities.Order;
import com.dosi.orderservice.entities.ProductItem;
import com.dosi.orderservice.enums.OrderStatus;
import com.dosi.orderservice.models.Customer;
import com.dosi.orderservice.models.Product;
import com.dosi.orderservice.repositories.OrderRepository;
import com.dosi.orderservice.repositories.ProductItemRepository;
import com.dosi.orderservice.services.CustomerRestClientService;
import com.dosi.orderservice.services.InventoryRestClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(OrderRepository orderRepository,
                            ProductItemRepository productItemRepository,
                            CustomerRestClientService customerRestClientService,
                            InventoryRestClientService inventoryRestClientService) {
        return args -> {
            List<Customer> allCustomers = customerRestClientService.allCustomers().getContent().stream().toList();
            List<Product> products = inventoryRestClientService.allProducts().getContent().stream().toList();
            Random random = new Random();
            for (int i = 0; i < allCustomers.size(); i++) {
                Order order = Order.builder()
                        .customerId(allCustomers.get(random.nextInt(allCustomers.size())).getId())
                        .status(Math.random() > 0.5 ? OrderStatus.PENDING : OrderStatus.CREATED)
                        .createdAt(new Date())
                        .build();
                orderRepository.save(order);
                for (int j = 0; j < products.size(); j++) {
                    if (Math.random() > 0.70) {
                        productItemRepository.save(
                                ProductItem.builder()
                                        .order(order)
                                        .productId(products.get(j).getId())
                                        .price(products.get(j).getPrice())
                                        .quantity(1 + random.nextInt(10 ))
                                        .build());
                    }

                }
            }
            allCustomers.forEach(customer -> {
            });
        };
    }

}
