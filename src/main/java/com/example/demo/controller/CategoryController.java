package com.example.demo.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/categories")
    @JsonView({ Views.CategorySimple.class })
    public Map<String, List<Category>> getAllCategories() {
        logger.info("GET /categories");

        return Collections.singletonMap("categories", categoryRepository.findAll());
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