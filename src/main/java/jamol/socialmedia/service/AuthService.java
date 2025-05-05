package jamol.socialmedia.service;

import jamol.socialmedia.dto.JwtResponseDTO;
import jamol.socialmedia.dto.LoginDTO;
import jamol.socialmedia.dto.RegisterDTO;
import jamol.socialmedia.entity.User;
import jamol.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Ro‘yxatdan o‘tish
    public JwtResponseDTO register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.username())) {
            throw new RuntimeException("Bunday foydalanuvchi mavjud!");
        }

        User user = User.builder()
                .username(registerDTO.username())
                .fullName(registerDTO.firstName() + " " + registerDTO.lastName())
                .email(registerDTO.email())
                .password(passwordEncoder.encode(registerDTO.password()))
                .role(registerDTO.role()) // <- User kiritgan rol
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());

        return new JwtResponseDTO(token, "Bearer", user.getId(), user.getUsername(), user.getEmail());
    }


    // Login qilish
    public JwtResponseDTO login(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.username(),
                        loginDTO.password()
                )
        );

        // Foydalanuvchini topish
        User user = userRepository.findByUsername(loginDTO.username())
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        String token = jwtService.generateToken(user.getUsername());

        return new JwtResponseDTO(token, "Bearer", user.getId(), user.getUsername(), user.getEmail());
    }
}
