package io.pactflow.example.kafka.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProductEventOld {
  private @Id String id;
  private String name;
  private String type;
  private String version;
  private String event;
  private Double price;

  public ProductEventOld() {}
  public ProductEventOld(String id, String name, String type, String version, String event, Double price) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.version = version;
    this.event = event;
    this.price = price;
  }
}