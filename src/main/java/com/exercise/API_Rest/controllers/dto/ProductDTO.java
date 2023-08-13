package com.exercise.API_Rest.controllers.dto;

import com.exercise.API_Rest.models.entities.Product;

public record ProductDTO(Long id, String name, Double price) {
  public Product toProduct() {
    return new Product(id, name, price);
  }
}
