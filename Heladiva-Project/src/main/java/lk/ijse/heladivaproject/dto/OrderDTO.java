package lk.ijse.heladivaproject.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private String orderId;

    private String userId;

    @NotNull(message = "Order date cannot be null.")
    private Date orderDate;

    @NotNull(message = "Total price cannot be null.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total price must be greater than or equal to 0.0.")
    private Double totalPrice;

    @NotNull( message = "Invalid status. Allowed values: PENDING, SHIPPED, DELIVERED, CANCELLED.")
    private String status;

    private List<OrderDetailsDTO> orderDetails;

    @Pattern(regexp = "^[A-Za-z ]{1,50}$", message = "First name must only contain letters and be 1-50 characters long.")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z ]{1,50}$", message = "Last name must only contain letters and be 1-50 characters long.")
    private String lastName;

    private String addressLine1;
    private String apartmentSuite;
    private String road;

    @Pattern(regexp = "^[0-9]{4,10}$", message = "Postal code must be numeric and 4-10 digits long.")
    private String postalCode;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format.")
    private String phone;

    @Email(message = "Invalid email format.")
    private String email;

    @Pattern(regexp = "^[A-Za-z ]{1,50}$", message = "Cardholder name must only contain letters and spaces.")
    private String cardholderName;

    @Pattern(regexp = "^[0-9]{16}$", message = "Card number must be a valid 16-digit number.")
    private String cardNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/([0-9]{2})$", message = "Expiry date must be in MM/YY format.")
    private String expiryDate;

    @Pattern(regexp = "^[0-9]{3,4}$", message = "CVC must be 3 or 4 digits.")
    private String cvc;

    @DecimalMin(value = "0.0", inclusive = true, message = "Trip price must be greater than or equal to 0.0.")
    private String tripPrice;

    @DecimalMin(value = "0.0", inclusive = true, message = "Trip duration must be greater than or equal to 0.0.")
    private String tripDuration;

    @DecimalMin(value = "0.0", inclusive = true, message = "Delivery fee must be greater than or equal to 0.0.")
    private String deliveryFee;

    @DecimalMin(value = "0.0", inclusive = true, message = "Tax must be greater than or equal to 0.0.")
    private String tax;
}
