package com.example.demo.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.entity.Spec;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SpecRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SpecRepository specRepository;

    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/customers")
    public Map<String, List<Product>> getAllCustomers() {
        logger.info("GET /customers");

        return Collections.singletonMap("customers", productRepository.findAll());
    }

    @PostMapping("/customers")
    public Product addCustomer(@RequestBody ProductDto product) {
        logger.info("POST /customers");

        Product persistentProduct = new Product();

        List<Spec> specs = product.getSpecs();
        for (Spec spec : specs)
            spec.setProduct(persistentProduct);

        List<Spec> persistentSpecs = specRepository.saveAll(product.getSpecs());

        persistentProduct.setName(product.getName());
        persistentProduct.setSpecs(persistentSpecs);
        return productRepository.save(persistentProduct);
    }
}
