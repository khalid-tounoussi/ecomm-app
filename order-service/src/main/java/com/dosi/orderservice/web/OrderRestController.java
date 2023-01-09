package com.dosi.orderservice.web;

import com.dosi.orderservice.entities.Order;
import com.dosi.orderservice.models.Customer;
import com.dosi.orderservice.models.Product;
import com.dosi.orderservice.repositories.OrderRepository;
import com.dosi.orderservice.repositories.ProductItemRepository;
import com.dosi.orderservice.services.CustomerRestClientService;
import com.dosi.orderservice.services.InventoryRestClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderRestController {
    private OrderRepository orderRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClientService customerRestClientService;
    private InventoryRestClientService inventoryRestClientService;

    @GetMapping("/fullOrder/{id}")
    public Order getOrder(@PathVariable Long id){
        Order order = orderRepository.findById(id).get();
        Customer customer = customerRestClientService.customerById(order.getCustomerId());
        order.setCustomer(customer);
        order.getProductItems().forEach( pi -> {
            Product product = inventoryRestClientService.productById( pi.getProductId() );
            pi.setProduct( product);
        });
        return order;
    }
}
