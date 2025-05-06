package jamol.socialmedia.controller;

import jamol.socialmedia.entity.User;
import jamol.socialmedia.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Admin rolini foydalanuvchiga tayinlash
    @PutMapping("/admin/{userId}")
    public User assignAdminRole(@PathVariable Long userId) {
        return adminService.assignAdminRole(userId);
    }
}
