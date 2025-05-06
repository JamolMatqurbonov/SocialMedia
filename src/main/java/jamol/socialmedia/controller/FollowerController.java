package jamol.socialmedia.controller;

import jamol.socialmedia.dto.FollowDTO;
import jamol.socialmedia.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowerController {

    private final FollowerService followerService;

    // Foydalanuvchini kuzatishni boshlash (follow)
    @PostMapping("/{followerId}/{followeeId}")
    public ResponseEntity<FollowDTO> followUser(@PathVariable Long followerId, @PathVariable Long followeeId) {
        FollowDTO followDTO = followerService.followUser(followerId, followeeId);
        return ResponseEntity.ok(followDTO);
    }

    // Foydalanuvchini kuzatishni to'xtatish (unfollow)
    @DeleteMapping("/{followerId}/{followeeId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long followerId, @PathVariable Long followeeId) {
        followerService.unfollowUser(followerId, followeeId);
        return ResponseEntity.ok("Foydalanuvchi kuzatilmayapti");
    }

    // Foydalanuvchining kuzatgan foydalanuvchilari (following) ro'yxatini olish
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<FollowDTO>> getFollowing(@PathVariable Long userId) {
        List<FollowDTO> following = followerService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }

    // Foydalanuvchini kuzatgan foydalanuvchilar (followers) ro'yxatini olish
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<FollowDTO>> getFollowers(@PathVariable Long userId) {
        List<FollowDTO> followers = followerService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }
}
