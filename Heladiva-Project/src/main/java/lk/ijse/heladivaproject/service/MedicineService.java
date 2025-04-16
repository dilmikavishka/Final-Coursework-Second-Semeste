package lk.ijse.heladivaproject.service;

import lk.ijse.heladivaproject.dto.MedicineDTO;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface MedicineService {
    ResponseEntity<String> save(MedicineDTO medicineDTO);

    ResponseEntity<String> update(MedicineDTO medicineDTO);

    void delete(String id);

    MedicineDTO getMedicineById(String id);

    Collection<MedicineDTO> getAllMedicalCures();
}
