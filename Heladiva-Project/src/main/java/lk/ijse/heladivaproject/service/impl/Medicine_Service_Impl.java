package lk.ijse.heladivaproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lk.ijse.heladivaproject.dto.MedicineDTO;
import lk.ijse.heladivaproject.entity.Medicine;
import lk.ijse.heladivaproject.entity.User;
import lk.ijse.heladivaproject.repo.MedicineDao;
import lk.ijse.heladivaproject.repo.UserDao;
import lk.ijse.heladivaproject.service.MedicineService;
import lk.ijse.heladivaproject.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class Medicine_Service_Impl implements MedicineService {
    private final Mapping mapping;
    private final UserDao userDao;
    private final MedicineDao medicineDao;

    private String generateMedicineId() {
        Medicine lastMedicine = medicineDao.findFirstByOrderByMedicineIdDesc();
        if (lastMedicine == null) {
            return "MED-001";
        }
        String lastMedicalId = lastMedicine.getMedicineId();
        int lastId = Integer.parseInt(lastMedicalId.split("-")[1]);
        int nextId = lastId + 1;
        return "MED-" + String.format("%03d", nextId);
    }


    @Override
    public ResponseEntity<String> save(MedicineDTO medicineDTO) {
        Optional<User> user = userDao.findById(medicineDTO.getAuthorId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Author not found");
        }
        Medicine medicine = mapping.toMedicine(medicineDTO);
        medicine.setMedicineId(generateMedicineId());
        medicine.setAuthor(user.get());
        medicineDao.save(medicine);
        return ResponseEntity.ok("Medicine saved successfully");
    }

    @Override
    public ResponseEntity<String> update(MedicineDTO medicineDTO) {
        Optional<Medicine> medicine = medicineDao.findById(medicineDTO.getMedicineId());
        if (medicine.isEmpty()) {
            return ResponseEntity.badRequest().body("Medicine not found");
        }
        Optional<User> user = userDao.findById(medicineDTO.getAuthorId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Author not found");
        }
        Medicine medicine1 = medicine.get();
        medicine1.setName(medicineDTO.getName());
        medicine1.setDisease(medicineDTO.getDisease());
        medicine1.setDescription(medicineDTO.getDescription());
        medicine1.setAllergies(medicineDTO.getAllergies());
        medicine1.setIngredients(medicineDTO.getIngredients());
        medicine1.setPreparation(medicineDTO.getPreparation());
        medicine1.setUsageInstructions(medicineDTO.getUsageInstructions());
        medicine1.setSideEffects(medicineDTO.getSideEffects());
        medicine1.setAuthor(user.get());
        medicineDao.save(medicine1);
        return ResponseEntity.ok("Medicine updated successfully");
    }

    @Override
    public void delete(String id) {
        String medId;
        try {
            medId =id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid article ID: " + id);
        }

        if (!medicineDao.existsById(medId)) {
            throw new EntityNotFoundException("Article not found with id: " + id);
        }

        medicineDao.deleteById(medId);
    }

    @Override
    public MedicineDTO getMedicineById(String id) {
        Optional<Medicine> medicine = medicineDao.findById(id);
        if (medicine.isEmpty()) {
            throw new EntityNotFoundException("Medicine not found with id: " + id);
        }
        return mapping.toMedicineDTO(medicine.get());
    }

    @Override
    public Collection<MedicineDTO> getAllMedicalCures() {
        return mapping.toMedicineDTOS(medicineDao.findAll());
    }
}
