package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Spec {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String name;
    String value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnoreProperties("specs")
    Product product;
}
