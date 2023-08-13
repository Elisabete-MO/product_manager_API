package com.exercise.API_Rest.controllers.dto;

import com.exercise.API_Rest.models.entities.Brand;
public record BrandDTO(Long id, String name) {
  public Brand toBrand() {
    return new Brand(id, name);
  }
}
