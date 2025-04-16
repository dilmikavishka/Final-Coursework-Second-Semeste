package lk.ijse.heladivaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Date orderDate;
    private Double totalPrice;
    private String status; // pending, processed,shipped, delivered
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetails;
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String apartmentSuite;
    private String road;
    private String postalCode;
    private String phone;
    private String email;
    private String cardholderName;
    private String cardNumber;
    private String expiryDate;
    private String cvc;
    private String tripPrice;
    private String tripDuration;
    private String deliveryFee;
    private String tax;
}
