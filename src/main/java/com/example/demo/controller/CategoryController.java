package com.example.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.SpecName;
import com.example.demo.jsonview.Views;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.SpecNameRepository;
import com.fasterxml.jackson.annotation.JsonView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SpecNameRepository specNameRepository;

    @JsonView({ Views.CategorySimple.class })
    @GetMapping("/categories/{id}")
    public Category getCategory(@PathVariable Long id, Pageable pageable) {
        logger.info("GET /categories/{}", id);

        Optional<Category> optionalProduct = categoryRepository.findById(id);
        if (!optionalProduct.isPresent())
            throw new NoSuchElementException("No category found");
        return optionalProduct.get();
    }

    @GetMapping("/categories")
    @JsonView({ Views.CategorySimple.class })
    public Page<Category> getAllCategories(Pageable pageable) {
        logger.info("GET /categories");

        return categoryRepository.findAll(pageable);
    }

    @PostMapping("/categories")
    @JsonView({ Views.CategorySimple.class })
    public Category createCategory(@RequestBody CategoryDto category) {
        logger.info("GET /categories");

        Category persistentCategory = new Category();
        persistentCategory.setName(category.getName());

        List<SpecName> specNames = category.getSpecNames();
        for (SpecName specName : specNames) {
            SpecName persistentSpecName = new SpecName();
            persistentSpecName.setName(specName.getName());
            persistentSpecName.setCategory(persistentCategory);
            specNameRepository.save(persistentSpecName);
        }

        List<SpecName> savedSpecNames = specNameRepository.saveAll(category.getSpecNames());
        persistentCategory.setSpecNames(savedSpecNames);
        return categoryRepository.save(persistentCategory);
    }
}
