package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Category;
import com.example.demo.entity.Spec;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private String name;
    private Category category;
    private List<Spec> specs;
}
