package com.example.demo.repository;

import com.example.demo.entity.Spec;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecRepository extends JpaRepository<Spec, Long> {
    
}
