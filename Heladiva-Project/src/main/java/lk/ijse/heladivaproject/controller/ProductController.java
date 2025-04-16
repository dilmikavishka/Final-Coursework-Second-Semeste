package lk.ijse.heladivaproject.controller;



import lk.ijse.heladivaproject.dto.ProductDTO;
import lk.ijse.heladivaproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:63342")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    public final ProductService productService;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/admin/save")
    public ResponseEntity<Map<String, Object>> saveArticle(@RequestBody ProductDTO productDTO) {
        log.info("Admin creating an product");
        ResponseEntity<String> serviceResponse = productService.save(productDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", serviceResponse.getStatusCode().is2xxSuccessful());
        response.put("message", "Product saved successfully!");
        return ResponseEntity.ok(response);
    }



    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/admin/update")
    public ResponseEntity<Map<String, Object>> updateArticle(@RequestBody ProductDTO productDTO) {
        log.info("Admin updating an product with id of" + productDTO.getProductId());
        ResponseEntity<String> serviceResponse = productService.update(productDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", serviceResponse.getStatusCode().is2xxSuccessful());
        response.put("message", "Product updated successfully!");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteArticle(@PathVariable String id) {
        log.info("Admin deleting product with ID: {}", id);
        productService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Product deleted successfully!");
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAnyRole('Admin', 'User')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getArticle(@PathVariable String id) {
        log.info("Fetching product with ID: {}", id);
        ProductDTO productDTO = productService.getProduct(id);
        return ResponseEntity.ok(productDTO);
    }

    @PreAuthorize("hasAnyRole('Admin', 'User')")
    @GetMapping("/getAll")
    public ResponseEntity<Collection<ProductDTO>> getAllArticles() {
        log.info("Fetching all products");
        Collection<ProductDTO> products = productService.getAll();
        return ResponseEntity.ok(products);
    }
}
