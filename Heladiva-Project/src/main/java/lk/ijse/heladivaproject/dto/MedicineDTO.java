package lk.ijse.heladivaproject.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicineDTO {
    @NotNull(message = "Medicine ID cannot be null.")
    private String medicineId;

    @NotBlank(message = "Medicine name cannot be empty.")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Medicine name must contain only letters and spaces.")
        private String name;

    @NotBlank(message = "Disease field cannot be empty.")
    @Pattern(regexp = "^[A-Za-z\\s,]+$", message = "Disease must contain only letters, spaces, and commas.")
    private String disease;

    @NotBlank(message = "Description cannot be empty.")
    private String description;

    @Pattern(regexp = "^[A-Za-z\\s,]*$", message = "Allergies must contain only letters, commas, and spaces.")
    private String allergies;

    private List<String> ingredients;

    @NotBlank(message = "Preparation instructions cannot be empty.")
    private String preparation;

    @NotBlank(message = "Usage instructions cannot be empty.")
    private String usageInstructions;

    private List<String> sideEffects;

    @NotBlank(message = "Author ID cannot be empty.")
    private String authorId;
}
