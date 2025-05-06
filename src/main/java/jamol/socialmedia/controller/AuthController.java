package jamol.socialmedia.controller;

import jamol.socialmedia.dto.JwtResponseDTO;
import jamol.socialmedia.dto.LoginDTO;
import jamol.socialmedia.dto.RegisterDTO;
import jamol.socialmedia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Foydalanuvchini ro'yxatdan o'tkazish endpointi.
     */
    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
        JwtResponseDTO response = authService.register(registerDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Login qilish endpointi.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            JwtResponseDTO response = authService.login(loginDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponseDTO(
                    null, null, null, null, e.getMessage()
            ));
        }
    }

}