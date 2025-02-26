package com.example.testes.repository;

import org.springframework.stereotype.Repository;

import com.example.testes.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
