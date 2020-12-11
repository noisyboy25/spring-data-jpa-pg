package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Spec {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String name;
    String value;

    @ManyToOne
    @JoinColumn
    Product product;
}