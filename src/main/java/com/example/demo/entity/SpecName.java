package com.example.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.demo.jsonview.Views;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SpecName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({ Views.ProductSimple.class, Views.CategorySimple.class })
    private long id;

    @JsonView({ Views.ProductSimple.class, Views.CategorySimple.class })
    private String name;

    @OneToMany(mappedBy = "specName")
    @JsonIgnoreProperties("specs")
    private List<Spec> specs;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnoreProperties("specNames")
    private Category category;
}
