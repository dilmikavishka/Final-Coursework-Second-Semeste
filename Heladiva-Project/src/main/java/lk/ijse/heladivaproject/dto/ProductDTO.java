package lk.ijse.heladivaproject.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {


    private String productId;

    @Pattern(regexp = "^[A-Za-z\\- ]{1,100}$", message = "Name must only contain letters, spaces, and hyphens (1-100 characters).")
    private String name;

    @Pattern(regexp = "^[A-Za-z\\- ]{1,100}$", message = "Scientific name must only contain letters, spaces, and hyphens (1-100 characters).")
    private String scientificName;

    @NotNull(message = "Invalid seasonality. Allowed values: SPRING, SUMMER, AUTUMN, WINTER.")
    private String seasonality;

    @Pattern(regexp = "^[A-Za-z\\- ]{1,100}$", message = "Location must only contain letters, spaces, and hyphens (1-100 characters).")
    private String location;

    private String uses;

    private String description;

    @Pattern(regexp = "^[A-Za-z\\- ]{1,50}$", message = "Type must only contain letters, spaces, and hyphens (1-50 characters).")
    private String type;

    private String healthBenefits;

    private String mapCoordinates;

    @NotNull(message = "Price cannot be null.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be greater than or equal to 0.0.")
    private Double price;

    @NotNull(message = "Stock availability cannot be null.")
    @Min(value = 0, message = "Stock availability must be greater than or equal to 0.")
    private Integer stockAvailability;

    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "Invalid image URL format.")
    private String ImageUrl;
}
