package jamol.socialmedia.service;

import jamol.socialmedia.entity.User;
import jamol.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    /**
     * Assigns the ADMIN role to a user by their ID.
     */
    public User assignAdminRole(Long userId) {
        // Find user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        // Assign ADMIN role (as plain String)
        user.setRole("ADMIN");

        // Save and return updated user
        return userRepository.save(user);
    }
}
