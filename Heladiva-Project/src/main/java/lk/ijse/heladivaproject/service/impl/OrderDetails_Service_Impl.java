package lk.ijse.heladivaproject.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.heladivaproject.entity.Article;
import lk.ijse.heladivaproject.entity.OrderDetails;
import lk.ijse.heladivaproject.repo.OrderDetailsDao;
import lk.ijse.heladivaproject.service.OrderDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderDetails_Service_Impl implements OrderDetailsService {
    private final OrderDetailsDao orderDetailsDao;

    private String generateNextArticleId() {
        OrderDetails lasOrderDetails = orderDetailsDao.findFirstByOrderByOrderDetailIdDesc();
        if (lasOrderDetails == null) {
            return "ODD-001";
        }
        String orderDetailId = lasOrderDetails.getOrderDetailId();
        int lastId = Integer.parseInt(orderDetailId.split("-")[1]);
        int nextId = lastId + 1;
        return "ODD-" + String.format("%03d", nextId);
    }

}
