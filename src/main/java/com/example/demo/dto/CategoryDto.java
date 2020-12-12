package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.SpecName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    String name;
    List<SpecName> specNames;
}
