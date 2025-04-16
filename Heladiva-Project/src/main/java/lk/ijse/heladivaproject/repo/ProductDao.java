package lk.ijse.heladivaproject.repo;


import jakarta.transaction.Transactional;
import lk.ijse.heladivaproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductDao extends JpaRepository<Product,String> {
    Product findFirstByOrderByProductIdDesc();
}
