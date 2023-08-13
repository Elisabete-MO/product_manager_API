package com.exercise.API_Rest.controllers;

import com.exercise.API_Rest.controllers.dto.CategoryDTO;
import com.exercise.API_Rest.controllers.dto.ResponseDTO;
import com.exercise.API_Rest.models.entities.Category;
import com.exercise.API_Rest.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<Category>> createCategory(@RequestBody CategoryDTO categoryDTO) {
    Category newCategory = categoryService.insertCategory(categoryDTO.toCategory());
    ResponseDTO<Category> responseDTO = new ResponseDTO<>("Categoria criada com sucesso!", newCategory);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<ResponseDTO<Category>> updateCategory(
      @PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO) {
    Optional<Category> optionalCategory = categoryService.updateCategory(categoryId, categoryDTO.toCategory());

    if (optionalCategory.isEmpty()) {
      ResponseDTO<Category> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrada a categoria de ID %d", categoryId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Category> responseDTO = new ResponseDTO<>(
        "Categoria atualizada com sucesso!", optionalCategory.get());
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<ResponseDTO<Category>> removeCategoryById(@PathVariable Long categoryId) {
    Optional<Category> optionalCategory = categoryService.removeCategoryById(categoryId);

    if (optionalCategory.isEmpty()) {
      ResponseDTO<Category> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrada a categoria de ID %d", categoryId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Category> responseDTO = new ResponseDTO<>("Categoria removida com sucesso!", null);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<ResponseDTO<Category>> getCategoryById(@PathVariable Long categoryId) {
    Optional<Category> optionalCategory = categoryService.getCategoryById(categoryId);

    if (optionalCategory.isEmpty()) {
      ResponseDTO<Category> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrada a categoria de ID %d", categoryId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Category> responseDTO = new ResponseDTO<>("Categoria encontrada com sucesso!", optionalCategory.get());
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping()
  public List<CategoryDTO> getAllCategories() {
    List<Category> allCategories = categoryService.getAllCategories();
    return allCategories.stream()
        .map((category) -> new CategoryDTO(category.getId(), category.getName()))
        .collect(Collectors.toList());
  }
}
