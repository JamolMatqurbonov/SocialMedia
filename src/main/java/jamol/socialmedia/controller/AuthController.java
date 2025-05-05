package jamol.socialmedia.controller;

import jamol.socialmedia.dto.JwtResponseDTO;
import jamol.socialmedia.dto.LoginDTO;
import jamol.socialmedia.dto.RegisterDTO;
import jamol.socialmedia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public JwtResponseDTO register(@RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }

    @PostMapping("/login")
    public JwtResponseDTO login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}
