package lk.ijse.heladivaproject.service.impl;



import jakarta.persistence.EntityNotFoundException;
import lk.ijse.heladivaproject.dto.UserDTO;
import lk.ijse.heladivaproject.entity.User;
import lk.ijse.heladivaproject.repo.UserDao;
import lk.ijse.heladivaproject.service.UserService;
import lk.ijse.heladivaproject.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class  User_Service_Impl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final Mapping mapping;

    @Override
    public UserDTO saveuser(UserDTO userDTOo) {
        return mapping.toUserDTO(userDao.save(mapping.toUser(userDTOo)));
    }

    @Override
    @Bean
    public UserDetailsService USER_DETAILS_SERVICE() {
        return username ->
                userDao.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return null;
    }

    @Override
    public boolean deleteUser(String userId) {
        return false;
    }

    @Override
    public User getUserEntityById(String email) {
        return null;
    }

    @Override
    public ResponseEntity<String> update(UserDTO userDTO) {
        System.out.println("📥 Received UserDTO:");
        System.out.println("UserID: " + userDTO.getUserId());
        System.out.println("Name: " + userDTO.getName());
        System.out.println("Email: " + userDTO.getEmail());
        System.out.println("Phone: " + userDTO.getPhone());
        System.out.println("Role: " + userDTO.getRole());
        System.out.println("Address: " + userDTO.getAddress());
        System.out.println("Image URL: " + userDTO.getImageUrl());
        Optional<User> optionalUser = userDao.findById(userDTO.getUserId());
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("User not found with id: " + userDTO.getUserId());
        }
        User user = optionalUser.get();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAddress(userDTO.getAddress());
        user.setRole(userDTO.getRole());
        user.setImageUrl(userDTO.getImageUrl());
        userDao.save(user);
        return ResponseEntity.ok("Profile updated successfully!");
    }
}
