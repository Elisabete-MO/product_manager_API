package com.exercise.API_Rest.services;

import com.exercise.API_Rest.models.entities.Brand;
import com.exercise.API_Rest.models.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
  private final BrandRepository brandRepository;

  @Autowired
  public BrandService(BrandRepository brandRepository) {
    this.brandRepository = brandRepository;
  }

  public Brand insertBrand(Brand brand) {
    return brandRepository.save(brand);
  }

  public Optional<Brand> updateBrand(Long id, Brand brand) {
    Optional<Brand> optionalBrand = brandRepository.findById(id);

    if (optionalBrand.isPresent()) {
      Brand brandFromDB = optionalBrand.get();
      brandFromDB.setName(brand.getName());

      Brand updatedBrand = brandRepository.save(brandFromDB);
      return Optional.of(updatedBrand);
    }
    return optionalBrand;
  }

  public Optional<Brand> removeBrandById(Long id) {
    Optional<Brand> brandOptional = brandRepository.findById(id);

    if(brandOptional.isPresent()) {
      brandRepository.deleteById(id);
    }
    return brandOptional;
  }

  public Optional<Brand> getBrandById(Long id) {
    return brandRepository.findById(id);
  }

  public List<Brand> getAllBrandies() {
    return brandRepository.findAll();
  }
}
