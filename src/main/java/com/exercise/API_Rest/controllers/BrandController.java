package com.exercise.API_Rest.controllers;

import com.exercise.API_Rest.controllers.dto.BrandDTO;
import com.exercise.API_Rest.controllers.dto.ResponseDTO;
import com.exercise.API_Rest.models.entities.Brand;
import com.exercise.API_Rest.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/brandies")
public class BrandController {

  private final BrandService brandService;

  @Autowired
  public BrandController(BrandService brandService) {
    this.brandService = brandService;
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<Brand>> createBrand(@RequestBody BrandDTO brandDTO) {
    Brand newBrand = brandService.insertBrand(brandDTO.toBrand());
    ResponseDTO<Brand> responseDTO = new ResponseDTO<>("Marca criada com sucesso!", newBrand);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PutMapping("/{brandId}")
  public ResponseEntity<ResponseDTO<Brand>> updateBrand(
      @PathVariable Long brandId, @RequestBody BrandDTO brandDTO) {
    Optional<Brand> optionalBrand = brandService.updateBrand(brandId, brandDTO.toBrand());

    if (optionalBrand.isEmpty()) {
      ResponseDTO<Brand> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrada a marca de ID %d", brandId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Brand> responseDTO = new ResponseDTO<>(
        "Marca atualizada com sucesso!", optionalBrand.get());
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{brandId}")
  public ResponseEntity<ResponseDTO<Brand>> removeBrandById(@PathVariable Long brandId) {
    Optional<Brand> optionalBrand = brandService.removeBrandById(brandId);

    if (optionalBrand.isEmpty()) {
      ResponseDTO<Brand> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrada a marca de ID %d", brandId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Brand> responseDTO = new ResponseDTO<>("Marca removida com sucesso!", null);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{brandId}")
  public ResponseEntity<ResponseDTO<Brand>> getBrandById(@PathVariable Long brandId) {
    Optional<Brand> optionalBrand = brandService.getBrandById(brandId);

    if (optionalBrand.isEmpty()) {
      ResponseDTO<Brand> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrada a marca de ID %d", brandId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Brand> responseDTO = new ResponseDTO<>("Marca encontrada com sucesso!", optionalBrand.get());
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping()
  public List<BrandDTO> getAllBrandies() {
    List<Brand> allBrands = brandService.getAllBrandies();
    return allBrands.stream()
        .map((brand) -> new BrandDTO(brand.getId(), brand.getName()))
        .collect(Collectors.toList());
  }
}
