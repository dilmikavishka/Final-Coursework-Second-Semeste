package lk.ijse.heladivaproject.reqAndresp.response;


import lk.ijse.heladivaproject.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTAuthResponse {
    private String token;
    private UserDTO user;
}

