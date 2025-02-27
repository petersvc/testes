package com.example.testes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testes.model.Product;
import com.example.testes.service.ProductService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> find(@PathVariable Long id) {
        var product = service.findById(id);
        return ResponseEntity.status(200).body(product);
    }
    
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        var products = service.findAll();
        return ResponseEntity.status(200).body(products);
    }

    @PostMapping
    public ResponseEntity<Product> save(
        @RequestParam @NotBlank String name,
        @RequestParam @NotNull Double price) {
        var product = service.save(name, price);
        return ResponseEntity.status(201).body(product);
    }
    
}
