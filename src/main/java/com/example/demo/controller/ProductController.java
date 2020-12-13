package com.example.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SpecNameRepository specNameRepository;

    @Autowired
    private SpecRepository specRepository;

    @JsonView({ Views.ProductSimple.class })
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id, Pageable pageable) {
        logger.info("GET /products/{}", id);

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            throw new NoSuchElementException("No product found");
        return optionalProduct.get();
    }

    @JsonView({ Views.ProductSimple.class })
    @GetMapping("/products")
    public Page<Product> getAllProducts(Pageable pageable) {
        logger.info("GET /products");

        return productRepository.findAll(pageable);
    }

    @JsonView({ Views.ProductSimple.class })
    @PostMapping("/products")
    public Product addCustomer(@RequestBody ProductDto product) {
        logger.info("POST /products");

        Category category = product.getCategory();
        Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
        if (!optionalCategory.isPresent()) {
            throw new DataIntegrityViolationException("No category found");
        }

        Category persistentCategory = optionalCategory.get();
        Product persistentProduct = new Product();

        List<Spec> specs = product.getSpecs();

        for (Spec spec : specs) {
            Optional<SpecName> optionalSpecName = specNameRepository.findById(spec.getSpecName().getId());
            if (!optionalSpecName.isPresent())
                throw new NoSuchElementException("No specification name found");

            SpecName persistentSpecName = optionalSpecName.get();

            if (!persistentSpecName.getCategory().getId().equals(persistentCategory.getId()))
                throw new DataIntegrityViolationException("Incorrect product category");

            spec.setSpecName(persistentSpecName);
            spec.setProduct(persistentProduct);
        }

        List<Spec> savedSpecs = specRepository.saveAll(product.getSpecs());

        persistentProduct.setName(product.getName());
        persistentProduct.setCategory(persistentCategory);
        persistentProduct.setSpecs(savedSpecs);
        return productRepository.save(persistentProduct);
    }
}
