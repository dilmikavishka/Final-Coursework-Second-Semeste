package lk.ijse.heladivaproject.repo;

import jakarta.transaction.Transactional;
import lk.ijse.heladivaproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OrderDao  extends JpaRepository<Order,String> {
    Order findFirstByOrderByOrderIdDesc();
}
