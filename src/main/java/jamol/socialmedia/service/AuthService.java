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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Ro‘yxatdan o‘tish metodi.
     * @param dto RegisterDTO obyekti (username, password, email, firstName, lastName, role)
     * @return JwtResponseDTO (token hamda foydalanuvchi ma'lumotlari)
     */
    public JwtResponseDTO register(RegisterDTO dto) {
        // 1. Username va email takrorlanmasligini tekshirish
        if (userRepository.existsByUsername(dto.username())) {
            throw new RuntimeException("Bunday foydalanuvchi mavjud!");
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Bu email allaqachon ro‘yxatdan o‘tgan!");
        }

        // 2. Parol bo‘sh bo‘lmasligi kerak
        if (dto.password() == null || dto.password().isBlank()) {
            throw new IllegalArgumentException("Parolni kiritish shart!");
        }

        // 3. Role qiymatini olish, agar bo‘sh bo‘lsa "USER" deb belgilash
        String roleStr = dto.role();
        if (roleStr == null || roleStr.isBlank()) {
            roleStr = "USER";
        } else {
            roleStr = roleStr.trim().toUpperCase();
        }
        // 4. Rolni validatsiya qilish: faqat "USER" yoki "ADMIN"
        if (!roleStr.equals("USER") && !roleStr.equals("ADMIN")) {
            throw new IllegalArgumentException("Noto‘g‘ri ro‘l: " + dto.role());
        }
// 5. Yangi foydalanuvchini yaratish
        User user = User.builder()
                .username(dto.username())
                .fullName(dto.firstName() + " " + dto.lastName())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password())) // Parolni kodlaymiz
                .role(roleStr)
                .blocked(false)
                .profilePictureUrl(null)
                .build();

// 6. Ma'lumotlar bazasiga saqlash
        userRepository.save(user);

// 7. JWT token yaratish
        String token = jwtService.generateToken(user.getUsername());

// 8. Token va foydalanuvchi ma'lumotlarini qaytarish
        return new JwtResponseDTO(
                token,
                "Bearer",
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    /**
     * Login qilish metodi.

     */
    public JwtResponseDTO login(LoginDTO loginDTO) {
        // 1. Foydalanuvchini username orqali tekshirish
        User user = userRepository.findByUsername(loginDTO.username())
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        // 2. Parolni tekshirish
        if (!passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            throw new RuntimeException("Parol noto‘g‘ri!");
        }

        // 3. JWT token yaratish
        String token = jwtService.generateToken(user.getUsername());

        // 4. Token va foydalanuvchi ma'lumotlarini qaytarish
        return new JwtResponseDTO(
                token,
                "Bearer",
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }


}