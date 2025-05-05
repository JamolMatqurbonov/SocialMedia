package jamol.socialmedia.service;

import jamol.socialmedia.dto.UserDTO;
import jamol.socialmedia.entity.User;
import jamol.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Barcha foydalanuvchilarni olish
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ID orqali foydalanuvchini olish
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));
        return mapToDTO(user);
    }

    // Foydalanuvchini o‘chirish
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Foydalanuvchi topilmadi");
        }
        userRepository.deleteById(id);
    }

    // Entity-ni DTO ga aylantirish
    private UserDTO mapToDTO(User user) {
        // fullName ni firstName va lastName ga ajratamiz
        String[] names = user.getFullName().split(" ", 2);
        String firstName = names.length > 0 ? names[0] : "";
        String lastName = names.length > 1 ? names[1] : "";

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                firstName,
                lastName,
                user.getEmail(),
                user.getProfilePictureUrl(),
                user.getRole()
        );
    }
}
