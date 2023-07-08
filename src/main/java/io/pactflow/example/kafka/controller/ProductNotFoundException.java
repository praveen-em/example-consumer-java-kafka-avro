package io.pactflow.example.kafka.controller;

public class ProductNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 8087803211710068858L;

  public ProductNotFoundException(String id) {
    super("Could not find product " + id);
  }
}