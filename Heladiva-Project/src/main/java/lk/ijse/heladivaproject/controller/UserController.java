package lk.ijse.heladivaproject.controller;


import lk.ijse.heladivaproject.dto.ContactDTO;
import lk.ijse.heladivaproject.dto.UserDTO;
import lk.ijse.heladivaproject.service.UserService;
import lk.ijse.heladivaproject.util.Mail;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:63342")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final Mail mailService;

    @PreAuthorize("hasAnyRole('Admin', 'User')")
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        log.info("Received update request for email: {}", userDTO.getEmail());

        ResponseEntity<String> serviceResponse = userService.update(userDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("success", serviceResponse.getStatusCode().is2xxSuccessful());
        response.put("message", "Profile updated successfully!");

        return ResponseEntity.ok(response);
    }


    @PostMapping("/sendEmail")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody ContactDTO contactDTO) {
        log.info("Received contact message from: {}", contactDTO.getEmail());

        try {
            mailService.sendContactMessage(contactDTO.getName(), contactDTO.getEmail(), contactDTO.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Message sent successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error sending contact message: {}", e.getMessage());

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to send message. Please try again.");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
