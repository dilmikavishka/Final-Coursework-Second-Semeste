package lk.ijse.heladivaproject.reqAndresp.secure;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignUp {
    private String password;
    private String email;
    private String role;
}
