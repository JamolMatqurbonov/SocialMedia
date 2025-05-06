package jamol.socialmedia.controller;

import jamol.socialmedia.entity.User;
import jamol.socialmedia.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * Bu endpoint foydalanuvchiga ADMIN rolini tayinlaydi.
     */
    @PutMapping("/users/{userId}/make-admin")
    public ResponseEntity<User> assignAdminRole(@PathVariable Long userId) {
        User updatedUser = adminService.assignAdminRole(userId);
        return ResponseEntity.ok(updatedUser);
    }
}
