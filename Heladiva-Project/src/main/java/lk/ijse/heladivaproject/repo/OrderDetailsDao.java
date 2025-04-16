package lk.ijse.heladivaproject.repo;

import jakarta.transaction.Transactional;
import lk.ijse.heladivaproject.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OrderDetailsDao extends JpaRepository<OrderDetails,String> {
    OrderDetails findFirstByOrderByOrderDetailIdDesc();
}
