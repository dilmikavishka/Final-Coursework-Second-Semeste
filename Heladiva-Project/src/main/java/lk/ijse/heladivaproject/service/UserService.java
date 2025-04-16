package lk.ijse.heladivaproject.service;




import lk.ijse.heladivaproject.dto.UserDTO;
import lk.ijse.heladivaproject.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface UserService {
    UserDTO saveuser(UserDTO userDTOo);
    UserDetailsService USER_DETAILS_SERVICE();

    Collection<UserDTO> getAllUsers();

    boolean deleteUser(String userId);


    User getUserEntityById(String email);

    ResponseEntity<String> update(UserDTO userDTO);
}
