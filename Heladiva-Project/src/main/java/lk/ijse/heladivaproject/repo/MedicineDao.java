package lk.ijse.heladivaproject.repo;

import lk.ijse.heladivaproject.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public interface MedicineDao extends JpaRepository<Medicine,String> {
    Medicine findFirstByOrderByMedicineIdDesc();
}
