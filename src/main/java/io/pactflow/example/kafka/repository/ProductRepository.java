package io.pactflow.example.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.pactflow.example.kafka.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}