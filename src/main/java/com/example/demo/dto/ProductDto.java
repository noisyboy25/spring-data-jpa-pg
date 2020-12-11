package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Spec;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    String name;
    List<Spec> specs;
}
