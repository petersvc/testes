package com.example.testes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testes.model.Product;
import com.example.testes.service.ProductService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> find(@PathParam("id") Long id) {
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
        @RequestParam @NotBlank(message = "The name is required") String name,
        @RequestParam @NotNull(message = "The price is required") Double price) {
        var product = service.save(name, price);
        return ResponseEntity.status(201).body(product);
    }
    
}
