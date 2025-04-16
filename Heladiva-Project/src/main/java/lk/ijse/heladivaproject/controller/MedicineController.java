package lk.ijse.heladivaproject.controller;


import lk.ijse.heladivaproject.dto.ArticleDTO;
import lk.ijse.heladivaproject.dto.MedicineDTO;
import lk.ijse.heladivaproject.service.MedicineService;
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
@RequestMapping("/api/medicine")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:63342")
public class MedicineController {
    private static final Logger log = LoggerFactory.getLogger(MedicineController.class);
    public final MedicineService medicineService;


    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/admin/save")
    public ResponseEntity<Map<String, Object>> saveArticle(@RequestBody MedicineDTO medicineDTO) {
        log.info("Admin creating an Medicine");
        ResponseEntity<String> serviceResponse = medicineService.save(medicineDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", serviceResponse.getStatusCode().is2xxSuccessful());
        response.put("message", "Medicine saved successfully!");
        return ResponseEntity.ok(response);
    }



    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/admin/update")
    public ResponseEntity<Map<String,Object>> updateArticle(@RequestBody MedicineDTO medicineDTO) {
        log.info("Admin updating medicine with ID: ");
        ResponseEntity<String> serviceResponse = medicineService.update(medicineDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", serviceResponse.getStatusCode().is2xxSuccessful());
        response.put("message", "Medicine Updated successfully!");
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteArticle(@PathVariable String id) {
        log.info("Admin deleting article with ID: {}", id);
        medicineService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Medicine deleted successfully!");
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAnyRole('Admin', 'User')")
    @GetMapping("/{id}")
    public ResponseEntity<MedicineDTO> getArticle(@PathVariable String id) {
        log.info("Fetching medicine with ID: {}", id);

        MedicineDTO articleDTO = medicineService.getMedicineById(id);

        return ResponseEntity.ok(articleDTO);
    }

    @PreAuthorize("hasAnyRole('Admin', 'User')")
    @GetMapping("/getAll")
    public ResponseEntity<Collection<MedicineDTO>> getAllArticles() {
        log.info("Fetching all medicines for common diseases");
        Collection<MedicineDTO> AllMedicalCures = medicineService.getAllMedicalCures();
        return ResponseEntity.ok(AllMedicalCures);
    }
}
