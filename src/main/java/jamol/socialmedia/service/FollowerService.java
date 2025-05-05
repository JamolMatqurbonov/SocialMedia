package jamol.socialmedia.service;

import jamol.socialmedia.dto.FollowDTO;
import jamol.socialmedia.entity.Follower;
import jamol.socialmedia.entity.User;
import jamol.socialmedia.repository.FollowerRepository;
import jamol.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    // Yangi kuzatishni yaratish
    public FollowDTO followUser(Long followerId, Long followeeId) {
        // Follower va Followee foydalanuvchilarini tekshiramiz
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower foydalanuvchi topilmadi"));
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new RuntimeException("Followee foydalanuvchi topilmadi"));

        // Foydalanuvchi allaqachon kuzatayotganini tekshiramiz
        if (followerRepository.existsByFollowerAndFollowee(follower, followee)) {
            throw new RuntimeException("Siz allaqachon bu foydalanuvchini kuzatayapsiz");
        }

        // Yangi Follower obyektini yaratamiz
        Follower followerEntity = Follower.builder()
                .follower(follower)
                .followee(followee)
                .followedAt(LocalDateTime.now())
                .build();

        followerEntity = followerRepository.save(followerEntity);

        // DTO shaklida qaytaramiz
        return mapToDTO(followerEntity);
    }

    // Kuzatilayotgan foydalanuvchini to'xtatish (unfollow)
    public void unfollowUser(Long followerId, Long followeeId) {
        // Follower va Followee foydalanuvchilarini tekshiramiz
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower foydalanuvchi topilmadi"));
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new RuntimeException("Followee foydalanuvchi topilmadi"));

        // Kuzatishni topib, o'chiramiz
        Follower followerEntity = followerRepository.findByFollowerAndFollowee(follower, followee)
                .orElseThrow(() -> new RuntimeException("Kuzatish mavjud emas"));

        followerRepository.delete(followerEntity);
    }

    // Foydalanuvchining kuzatganlarni olish (following)
    public List<FollowDTO> getFollowing(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        List<Follower> followees = followerRepository.findByFollower(user);

        return followees.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Foydalanuvchini kuzatganlarni olish (followers)
    public List<FollowDTO> getFollowers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        List<Follower> followers = followerRepository.findByFollowee(user);

        return followers.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Entity-ni DTO ga aylantirish
    private FollowDTO mapToDTO(Follower followerEntity) {
        return new FollowDTO(
                followerEntity.getId(),
                followerEntity.getFollower().getId(),
                followerEntity.getFollowee().getId(),
                followerEntity.getFollowedAt()
        );
    }
}
