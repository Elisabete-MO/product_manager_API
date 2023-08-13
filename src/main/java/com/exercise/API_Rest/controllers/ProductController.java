package com.exercise.API_Rest.controllers;

import com.exercise.API_Rest.controllers.dto.ProductDTO;
import com.exercise.API_Rest.controllers.dto.ResponseDTO;
import com.exercise.API_Rest.models.entities.Product;
import com.exercise.API_Rest.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<Product>> createProduct(@RequestBody ProductDTO productDTO) {
    Product newProduct = productService.insertProduct(productDTO.toProduct());
    ResponseDTO<Product> responseDTO = new ResponseDTO<>("Produto criado com sucesso!", newProduct);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ResponseDTO<Product>> updateProduct(
      @PathVariable Long productId, @RequestBody ProductDTO productDTO) {
    Optional<Product> optionalProduct = productService.updateProduct(productId, productDTO.toProduct());

    if (optionalProduct.isEmpty()) {
      ResponseDTO<Product> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrado o produto de ID %d", productId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Product> responseDTO = new ResponseDTO<>(
        "Produto atualizado com sucesso!", optionalProduct.get());
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<ResponseDTO<Product>> removeProductById(@PathVariable Long productId) {
    Optional<Product> optionalProduct = productService.removeProductById(productId);

    if (optionalProduct.isEmpty()) {
      ResponseDTO<Product> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrado o produto de ID %d", productId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Product> responseDTO = new ResponseDTO<>("Produto removido com sucesso!", null);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ResponseDTO<Product>> getProductById(@PathVariable Long productId) {
    Optional<Product> optionalProduct = productService.getProductById(productId);

    if (optionalProduct.isEmpty()) {
      ResponseDTO<Product> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrado o produto de ID %d", productId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Product> responseDTO = new ResponseDTO<>("Produto encontrado com sucesso!", optionalProduct.get());
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping()
  public List<ProductDTO> getAllProducts() {
    List<Product> allProducts = productService.getAllProducts();
    return allProducts.stream()
        .map((product) -> new ProductDTO(product.getId(), product.getName(), product.getPrice()))
        .collect(Collectors.toList());
  }
}
