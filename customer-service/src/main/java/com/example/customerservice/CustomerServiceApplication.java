package com.example.customerservice;

import com.example.customerservice.entities.Customer;
import com.example.customerservice.repositories.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    List<Customer> buildData(){
        Faker faker = new Faker();
        List customers = new ArrayList();
        for (int i = 0; i < 6; i++) {
            String email = faker.internet().emailAddress();
            customers.add(new Customer(null, email.split("@")[0], email));
        }
        return customers;
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository) {
        return args -> {
            customerRepository.saveAll(buildData());
            customerRepository.findAll().forEach(System.out::println);
        };
    }

}
