package com.example.demo.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.Spec;
import com.example.demo.entity.SpecName;
import com.example.demo.jsonview.Views;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SpecNameRepository;
import com.example.demo.repository.SpecRepository;
import com.fasterxml.jackson.annotation.JsonView;

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
    CategoryRepository categoryRepository;

    @Autowired
    SpecNameRepository specNameRepository;

    @Autowired
    SpecRepository specRepository;

    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }

    @JsonView(Views.ProductSimple.class)
    @GetMapping("/products")
    public Map<String, List<Product>> getAllProducts() {
        logger.info("GET /products");

        return Collections.singletonMap("products", productRepository.findAll());
    }

    @JsonView(Views.ProductSimple.class)
    @PostMapping("/products")
    public Product addCustomer(@RequestBody ProductDto product) {
        logger.info("POST /products");
        logger.info("Debug - product: {}", product.getSpecs());

        Product persistentProduct = new Product();

        List<Spec> specs = product.getSpecs();

        Category category = product.getCategory();
        Category persistentCategory = categoryRepository.save(category);

        for (Spec spec : specs) {
            // Optional<SpecName> optionalSpecName =
            // specNameRepository.findById(spec.getSpecName().getId());
            // if (!optionalSpecName.isPresent())
            // throw new NoSuchElementException("No specification name found");

            SpecName persistentSpecName = new SpecName();
            persistentSpecName.setCategory(persistentCategory);
            persistentSpecName.setName(spec.getSpecName().getName());
            SpecName specName = specNameRepository.save(persistentSpecName);

            // if (specName.getCategory().getId().equals(product.getCategory().getId()))
            // throw new DataIntegrityViolationException("No specification name found");

            specName.setCategory(persistentCategory);
            spec.setSpecName(specName);
            spec.setProduct(persistentProduct);
        }

        List<Spec> persistentSpecs = specRepository.saveAll(product.getSpecs());

        persistentProduct.setName(product.getName());
        persistentProduct.setCategory(persistentCategory);
        persistentProduct.setSpecs(persistentSpecs);
        return productRepository.save(persistentProduct);
    }
}
