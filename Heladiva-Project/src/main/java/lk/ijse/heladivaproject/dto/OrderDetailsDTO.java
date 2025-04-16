package lk.ijse.heladivaproject.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsDTO {
    @NotNull(message = "Order Detail ID cannot be null.")
    private String orderDetailId;
    @NotBlank(message = "Order ID cannot be empty.")
    private String orderId;
    @NotBlank(message = "Product ID cannot be empty.")
    private String productId;

    @NotNull(message = "Quantity cannot be null.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    private Integer quantity;

    @NotNull(message = "Price cannot be null.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be greater than or equal to 0.0.")
    private Double price;
}
