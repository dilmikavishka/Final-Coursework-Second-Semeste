package lk.ijse.heladivaproject.service;

import lk.ijse.heladivaproject.dto.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ProductService {
    ResponseEntity<String> save(ProductDTO productDTO);

    ResponseEntity<String> update(ProductDTO productDTO);

    void delete(String id);

    ProductDTO getProduct(String id);

    Collection<ProductDTO> getAll();
}
