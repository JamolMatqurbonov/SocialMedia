package jamol.socialmedia.controller;

import jamol.socialmedia.dto.UserDTO;
import jamol.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    /**
     * Barcha foydalanuvchilarni olish.
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * ID orqali foydalanuvchini olish.
     */
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Foydalanuvchini o‘chirish.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
