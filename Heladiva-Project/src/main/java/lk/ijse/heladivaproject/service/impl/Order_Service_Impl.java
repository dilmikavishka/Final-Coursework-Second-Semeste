package lk.ijse.heladivaproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lk.ijse.heladivaproject.dto.OrderDTO;
import lk.ijse.heladivaproject.dto.OrderDetailsDTO;
import lk.ijse.heladivaproject.entity.Order;
import lk.ijse.heladivaproject.entity.OrderDetails;
import lk.ijse.heladivaproject.entity.Product;
import lk.ijse.heladivaproject.entity.User;
import lk.ijse.heladivaproject.repo.OrderDao;
import lk.ijse.heladivaproject.repo.OrderDetailsDao;
import lk.ijse.heladivaproject.repo.ProductDao;
import lk.ijse.heladivaproject.repo.UserDao;
import lk.ijse.heladivaproject.service.OrderService;
import lk.ijse.heladivaproject.util.Mail;
import lk.ijse.heladivaproject.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class Order_Service_Impl implements OrderService {
    private final OrderDao orderDao;
    private final OrderDetailsDao orderDetailsDao;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final Mail emailService;

    private String generateNextOrderId() {
        Order lastOrder = orderDao.findFirstByOrderByOrderIdDesc();
        if (lastOrder == null) {
            return "ODR-001";
        }
        int lastId = Integer.parseInt(lastOrder.getOrderId().split("-")[1]);
        return String.format("ODR-%03d", lastId + 1);
    }

    private int orderDetailCounter = 0;

    private String generateNextOrderDetailId() {
        if (orderDetailCounter == 0) {
            OrderDetails lastOrderDetails = orderDetailsDao.findFirstByOrderByOrderDetailIdDesc();
            if (lastOrderDetails == null || lastOrderDetails.getOrderDetailId() == null) {
                orderDetailCounter = 1;
            } else {
                String lastIdStr = lastOrderDetails.getOrderDetailId();
                if (!lastIdStr.startsWith("ODD-")) {
                    throw new IllegalStateException("Invalid OrderDetail ID format: " + lastIdStr);
                }
                orderDetailCounter = Integer.parseInt(lastIdStr.split("-")[1]) + 1;
            }
        }
        return String.format("ODD-%03d", orderDetailCounter++);
    }

    @Transactional
    @Override
    public ResponseEntity<String> save(OrderDTO orderDTO) {
        try {
            Order order = new Order();
            order.setOrderId(generateNextOrderId());
            order.setOrderDate(orderDTO.getOrderDate());
            order.setTotalPrice(orderDTO.getTotalPrice());
            order.setStatus(orderDTO.getStatus());
            order.setFirstName(orderDTO.getFirstName());
            order.setLastName(orderDTO.getLastName());
            order.setAddressLine1(orderDTO.getAddressLine1());
            order.setApartmentSuite(orderDTO.getApartmentSuite());
            order.setRoad(orderDTO.getRoad());
            order.setPostalCode(orderDTO.getPostalCode());
            order.setPhone(orderDTO.getPhone());
            order.setEmail(orderDTO.getEmail());
            order.setCardholderName(orderDTO.getCardholderName());
            order.setCardNumber(orderDTO.getCardNumber());
            order.setExpiryDate(orderDTO.getExpiryDate());
            order.setCvc(orderDTO.getCvc());
            order.setTripPrice(orderDTO.getTripPrice());
            order.setTripDuration(orderDTO.getTripDuration());
            order.setDeliveryFee(orderDTO.getDeliveryFee());
            order.setTax(orderDTO.getTax());

            Optional<User> optionalUser = userDao.findById(orderDTO.getUserId());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
            }
            User user = optionalUser.get();
            order.setUser(user);

            orderDao.save(order);

            StringBuilder productDetails = new StringBuilder();
            for (OrderDetailsDTO orderDetailsDTO : orderDTO.getOrderDetails()) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderDetailId(generateNextOrderDetailId());
                orderDetails.setOrder(order);
                orderDetails.setQuantity(orderDetailsDTO.getQuantity());
                orderDetails.setPrice(orderDetailsDTO.getPrice());

                Optional<Product> optionalProduct = productDao.findById(orderDetailsDTO.getProductId());
                if (optionalProduct.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found");
                }
                Product product = optionalProduct.get();


                if (product.getStockAvailability() < orderDetailsDTO.getQuantity()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Insufficient stock for product: " + product.getName());
                }

                product.setStockAvailability(product.getStockAvailability() - orderDetailsDTO.getQuantity());
                productDao.save(product);

                orderDetails.setProduct(product);
                orderDetailsDao.save(orderDetails);

                productDetails.append("<tr>")
                        .append("<td style='padding: 10px; border-bottom: 1px solid #ddd;'>" + product.getName() + "</td>")
                        .append("<td style='padding: 10px; border-bottom: 1px solid #ddd;'>" + orderDetailsDTO.getQuantity() + "</td>")
                        .append("<td style='padding: 10px; border-bottom: 1px solid #ddd;'>LKR " + orderDetailsDTO.getPrice() + "</td>")
                        .append("</tr>");
            }

            emailService.sendOrderConfirmation(user.getEmail(), order.getOrderId(), order.getTotalPrice(), productDetails.toString());
            System.out.println("Email sent successfully to: " + user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Order saved successfully");
        } catch (Exception e) {
            System.err.println("Error processing order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving order: " + e.getMessage());
        }
    }

    @Override
    public OrderDTO getById(String orderId) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setUserId(order.getUser().getUserId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setStatus(order.getStatus());


        orderDTO.setFirstName(order.getFirstName());
        orderDTO.setLastName(order.getLastName());
        orderDTO.setAddressLine1(order.getAddressLine1());
        orderDTO.setApartmentSuite(order.getApartmentSuite());
        orderDTO.setRoad(order.getRoad());
        orderDTO.setPostalCode(order.getPostalCode());
        orderDTO.setPhone(order.getPhone());
        orderDTO.setEmail(order.getEmail());
        orderDTO.setCardholderName(order.getCardholderName());
        orderDTO.setCardNumber(order.getCardNumber());
        orderDTO.setExpiryDate(order.getExpiryDate());
        orderDTO.setCvc(order.getCvc());
        orderDTO.setTripPrice(order.getTripPrice());
        orderDTO.setTripDuration(order.getTripDuration());
        orderDTO.setDeliveryFee(order.getDeliveryFee());
        orderDTO.setTax(order.getTax());

        if (order.getOrderDetails() != null) {
            List<OrderDetailsDTO> orderDetailsDTOList = order.getOrderDetails().stream().map(orderDetail -> {
                OrderDetailsDTO dto = new OrderDetailsDTO();
                dto.setOrderDetailId(orderDetail.getOrderDetailId());
                dto.setOrderId(orderDetail.getOrder().getOrderId());
                dto.setProductId(orderDetail.getProduct().getProductId());
                dto.setQuantity(orderDetail.getQuantity());
                dto.setPrice(orderDetail.getPrice());
                return dto;
            }).collect(Collectors.toList());

            orderDTO.setOrderDetails(orderDetailsDTOList);
        }

        return orderDTO;
    }


    @Override
    public List<OrderDTO> getAll() {
        List<Order> orders = orderDao.findAll();

        return orders.stream().map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setUserId(order.getUser().getUserId());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setTotalPrice(order.getTotalPrice());
            orderDTO.setStatus(order.getStatus());

            // Setting additional fields
            orderDTO.setFirstName(order.getFirstName());
            orderDTO.setLastName(order.getLastName());
            orderDTO.setAddressLine1(order.getAddressLine1());
            orderDTO.setApartmentSuite(order.getApartmentSuite());
            orderDTO.setRoad(order.getRoad());
            orderDTO.setPostalCode(order.getPostalCode());
            orderDTO.setPhone(order.getPhone());
            orderDTO.setEmail(order.getEmail());
            orderDTO.setCardholderName(order.getCardholderName());
            orderDTO.setCardNumber(order.getCardNumber());
            orderDTO.setExpiryDate(order.getExpiryDate());
            orderDTO.setCvc(order.getCvc());
            orderDTO.setTripPrice(order.getTripPrice());
            orderDTO.setTripDuration(order.getTripDuration());
            orderDTO.setDeliveryFee(order.getDeliveryFee());
            orderDTO.setTax(order.getTax());


            if (order.getOrderDetails() != null) {
                List<OrderDetailsDTO> orderDetailsDTOList = order.getOrderDetails().stream().map(orderDetail -> {
                    OrderDetailsDTO dto = new OrderDetailsDTO();
                    dto.setOrderDetailId(orderDetail.getOrderDetailId());
                    dto.setOrderId(orderDetail.getOrder().getOrderId());
                    dto.setProductId(orderDetail.getProduct().getProductId());
                    dto.setQuantity(orderDetail.getQuantity());
                    dto.setPrice(orderDetail.getPrice());
                    return dto;
                }).collect(Collectors.toList());

                orderDTO.setOrderDetails(orderDetailsDTOList);
            }

            return orderDTO;
        }).collect(Collectors.toList());
    }


    @Override
    public ResponseEntity<String> updateOrderStatus(String orderId, String status) {
        Optional<Order> optionalOrder = orderDao.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }

        Order order = optionalOrder.get();
        order.setStatus(status);
        orderDao.save(order);

        try {
            String emailBody = "<p>Your order <b>" + orderId + "</b> has been updated to <b>" + status + "</b>.</p>";
            emailService.sendOrderUpdate(order.getEmail(), orderId, status, emailBody);
            System.out.println("Order update email sent to: " + order.getEmail());
        } catch (Exception e) {
            System.err.println("Error sending order update email: " + e.getMessage());
        }

        return ResponseEntity.ok("Order status updated successfully.");
    }

    @Override
    public ResponseEntity<String> deleteOrder(String orderId) {
        Optional<Order> optionalOrder = orderDao.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }

        Order order = optionalOrder.get();
        orderDetailsDao.deleteAll(order.getOrderDetails());
        orderDao.delete(order);

        return ResponseEntity.ok("Order deleted successfully.");
    }
}
