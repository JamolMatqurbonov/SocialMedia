package jamol.socialmedia.service;

import jamol.socialmedia.entity.Role;
import jamol.socialmedia.entity.User;
import jamol.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    // Admin rolini foydalanuvchiga tayinlash
    public User assignAdminRole(Long userId) {
        // Foydalanuvchini ID bo‘yicha qidirish
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        // Foydalanuvchini admin qilib o‘zgartirish
        user.setRole(Role.ADMIN);

        // Yangilangan foydalanuvchini saqlash
        return userRepository.save(user);
    }
}
