package lk.ijse.heladivaproject.service.impl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lk.ijse.heladivaproject.dto.ProductDTO;
import lk.ijse.heladivaproject.entity.Product;
import lk.ijse.heladivaproject.repo.ProductDao;
import lk.ijse.heladivaproject.service.ProductService;
import lk.ijse.heladivaproject.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class Product_Service_Impl implements ProductService {
    private final ProductDao productDao;
    private final Mapping mapping;

    private String generateNextProductId() {
        Product lastProduct = productDao.findFirstByOrderByProductIdDesc();
        if (lastProduct == null) {
            return "PRT-001";
        }
        String lastProductProductId = lastProduct.getProductId();
        int lastId = Integer.parseInt(lastProductProductId.split("-")[1]);
        int nextId = lastId + 1;
        return "PRT-" + String.format("%03d", nextId);
    }

    @Override
    public ResponseEntity<String> save(ProductDTO productDTO) {
        Product product = mapping.toProduct(productDTO);
        product.setProductId(generateNextProductId());
        productDao.save(product);
        return ResponseEntity.ok("Product Saved Successfully");
    }

    @Override
    public ResponseEntity<String> update(ProductDTO productDTO) {
        Optional<Product> optionalProduct = productDao.findById(productDTO.getProductId());
        if (!optionalProduct.isPresent()){
            throw new EntityNotFoundException("Product was not found within the database" + productDTO.getProductId());
        }
        Product product = optionalProduct.get();
        product.setName(productDTO.getName());
        product.setScientificName(productDTO.getScientificName());
        product.setSeasonality(productDTO.getSeasonality());
        product.setLocation(productDTO.getLocation());
        product.setUses(productDTO.getUses());
        product.setDescription(productDTO.getDescription());
        product.setHealthBenefits(productDTO.getHealthBenefits());
        product.setMapCoordinates(productDTO.getMapCoordinates());
        product.setPrice(productDTO.getPrice());
        product.setStockAvailability(productDTO.getStockAvailability());
        product.setImageUrl(productDTO.getImageUrl());
        productDao.saveAndFlush(product);
        return ResponseEntity.ok("Product updated successfully!");
    }

    @Override
    public void delete(String id) {
        String productId;
        try {
            productId =id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid product ID: " + id);
        }

        if (!productDao.existsById(productId)) {
            throw new EntityNotFoundException("product not found with id: " + id);
        }

        productDao.deleteById(productId);
    }

    @Override
    public ProductDTO getProduct(String id) {
        Optional<Product> optionalProduct = productDao.findById(id);
        if (!optionalProduct.isPresent()){
            throw new EntityNotFoundException("Product not found within the database");
        }
        return mapping.toProductDTO(optionalProduct.get());
    }

    @Override
    public Collection<ProductDTO> getAll() {
        List<Product> allProducts = productDao.findAll();
        return mapping.toProductDTOS(allProducts);
    }
}
