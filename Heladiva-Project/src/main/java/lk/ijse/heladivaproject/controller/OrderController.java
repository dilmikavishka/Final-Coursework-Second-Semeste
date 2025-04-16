package lk.ijse.heladivaproject.controller;

import lk.ijse.heladivaproject.dto.ArticleDTO;
import lk.ijse.heladivaproject.dto.OrderDTO;
import lk.ijse.heladivaproject.entity.Order;
import lk.ijse.heladivaproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Order")
@CrossOrigin(origins = "http://localhost:63342")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('Admin', 'User')")
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveArticle(@RequestBody OrderDTO orderDTO) {
        ResponseEntity<String> SavedResponse = orderService.save(orderDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", SavedResponse.getStatusCode().is2xxSuccessful());
        response.put("message", "Order Was created  successfully!");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @PutMapping("/admin/update/{orderId}/{status}")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(@PathVariable String orderId, @PathVariable String status) {
        ResponseEntity<String> updateResponse = orderService.updateOrderStatus(orderId, status);
        Map<String, Object> response = new HashMap<>();
        response.put("success", updateResponse.getStatusCode().is2xxSuccessful());
        response.put("message", "Order Status Updated Successfully!");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/admin/getAll")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PreAuthorize("hasAnyRole('Admin', 'User')")
    @GetMapping("/getById/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getById(orderId));
    }
}


