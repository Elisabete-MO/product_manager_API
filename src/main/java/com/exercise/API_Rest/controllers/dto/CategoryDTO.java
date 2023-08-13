package com.exercise.API_Rest.controllers.dto;

import com.exercise.API_Rest.models.entities.Category;

public record CategoryDTO(Long id, String name) {
  public Category toCategory() {
    return new Category(id, name);
  }
}
