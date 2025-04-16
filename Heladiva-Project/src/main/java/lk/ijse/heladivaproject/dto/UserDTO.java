package lk.ijse.heladivaproject.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String userId;

    @Pattern(regexp = "^[A-Za-z\\- ]{1,100}$", message = "Name must only contain letters, spaces, and hyphens (1-100 characters).")
    private String name;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email cannot be blank.")
    private String email;

    @Pattern(regexp = "^(\\+94|0)[1-9][0-9]{8}$", message = "Invalid phone number. Must be a valid Sri Lankan number.")
    private String phone;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must have at least 8 characters, including 1 uppercase, 1 lowercase, 1 digit, and 1 special character."
    )
    private String password;

    private String address;

    @NotNull( message = "Role must be either ADMIN or USER.")
    private String role;

    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "Invalid image URL format.")
    private String imageUrl;
}
