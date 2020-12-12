package com.example.demo.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.demo.jsonview.Views;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({ Views.CategorySimple.class, Views.ProductSimple.class })
    private Long id;

    @JsonView({ Views.CategorySimple.class, Views.ProductSimple.class })
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties("category")
    @JsonView({ Views.CategorySimple.class })
    private List<Product> products;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties("category")
    @JsonView({ Views.CategorySimple.class })
    private List<SpecName> specNames;
}
