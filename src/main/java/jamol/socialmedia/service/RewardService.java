package jamol.socialmedia.service;

import jamol.socialmedia.dto.RewardDTO;
import jamol.socialmedia.entity.Reward;
import jamol.socialmedia.entity.User;
import jamol.socialmedia.repository.RewardRepository;
import jamol.socialmedia.repository.UserRepository;
import jamol.socialmedia.repository.PostRepository;
import jamol.socialmedia.dto.MessageRequestDTO;
import jamol.socialmedia.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MessageService messageService;  // MessageService qo'shildi

    // Foydalanuvchiga mukofot berish va xabar yuborish
    public RewardDTO awardReward(Long userId) {
        // Foydalanuvchini olish
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        // Obunachilar sonini tekshirish
        int followersCount = user.getFollowers().size();  // user.getFollowers() orqali obunachilarni olish
        String rewardMessage = "";

        // Obunachilarga qarab mukofot belgilash
        if (followersCount >= 10000) {
            rewardMessage = "Tabriklayman, 10000 ta boldingiz!";
        } else if (followersCount >= 1000) {
            rewardMessage = "Tabriklayman, 1000 ta boldingiz!";
        } else if (followersCount >= 100) {
            rewardMessage = "Tabriklayman, 100 ta boldingiz!";
        }

        // Postlar sonini tekshirish
        Long postsCount = postRepository.countByUserId(userId);  // userId bo'yicha postlar sonini olish
        if (postsCount >= 1000) {
            rewardMessage += " 1000 ta post qo'ydingiz!";
        } else if (postsCount >= 100) {
            rewardMessage += " 100 ta post qo'ydingiz!";
        } else if (postsCount >= 10) {
            rewardMessage += " 10 ta post qo'ydingiz!";
        }

        // Mukofotni yaratish
        Reward reward = Reward.builder()
                .user(user)
                .reason(rewardMessage)
                .build();

        // Mukofotni saqlash
        reward = rewardRepository.save(reward);

        // Xabar yuborish (MessageRequestDTO yaratish)
        MessageRequestDTO messageDTO = new MessageRequestDTO(
                user.getId(),  // senderId
                user.getId(),  // receiverId (foydalanuvchi o'ziga yuboradi)
                rewardMessage  // Xabar mazmuni
        );

        // Xabarni yuborish
        messageService.sendMessage(messageDTO);

        // DTO shaklida qaytarish
        return new RewardDTO(reward.getId(), reward.getReason());
    }
}
