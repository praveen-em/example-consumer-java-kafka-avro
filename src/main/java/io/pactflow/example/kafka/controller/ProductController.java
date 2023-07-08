package io.pactflow.example.kafka.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pactflow.example.kafka.model.Product;
import io.pactflow.example.kafka.repository.ProductRepository;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(value = "/", produces = "application/json; charset=utf-8")
class ProductController {

  private final ProductRepository repository;

  ProductController(ProductRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/products")
  List<Product> all() {
    return repository.findAll();
  }

  @GetMapping({ "/product/{id}" })
  Product one(@PathVariable String id) {
    return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
  }

}