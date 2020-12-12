package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.jsonview.Views;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Spec {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonView({ Views.ProductSimple.class })
    private String value;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("specs")
    @JsonView({ Views.ProductSimple.class })
    private SpecName specName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnoreProperties("specs")
    private Product product;
}
