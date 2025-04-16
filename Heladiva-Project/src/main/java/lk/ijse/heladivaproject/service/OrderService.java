package lk.ijse.heladivaproject.service;

import lk.ijse.heladivaproject.dto.OrderDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<String> save(OrderDTO orderDTO);
    OrderDTO getById(String orderId);
    List<OrderDTO> getAll();
    ResponseEntity<String> updateOrderStatus(String orderId, String status);
    ResponseEntity<String> deleteOrder(String orderId);
}
