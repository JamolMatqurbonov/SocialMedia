package jamol.socialmedia.service;

import jamol.socialmedia.dto.ResponseDTO;
import jamol.socialmedia.entity.Chat;
import jamol.socialmedia.entity.User;
import jamol.socialmedia.repository.ChatRepository;
import jamol.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    // Yangi xabar yuborish
    public ResponseDTO sendMessage(Long senderId, Long receiverId, String messageContent) {
        // Foydalanuvchilarni tekshirish
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Qabul qiluvchi foydalanuvchi topilmadi"));

        // Xabar yaratish
        Chat chatMessage = Chat.builder()
                .sender(sender)
                .receiver(receiver)
                .messageContent(messageContent)
                .timestamp(LocalDateTime.now())
                .build();

        // Xabarni saqlash
        chatRepository.save(chatMessage);

        // Javob qaytarish
        return new ResponseDTO("Xabar muvaffaqiyatli yuborildi", true);
    }

    // Foydalanuvchining barcha xabarlarini olish
    public List<Chat> getMessages(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        // Foydalanuvchining yuborgan va olgan barcha xabarlarini olish
        return chatRepository.findBySenderOrReceiver(user, user);
    }

    // Foydalanuvchining boshqa foydalanuvchi bilan muloqotini olish
    public List<Chat> getChatHistory(Long userId, Long otherUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));
        User otherUser = userRepository.findById(otherUserId)
                .orElseThrow(() -> new RuntimeException("Boshqa foydalanuvchi topilmadi"));

        // Foydalanuvchi va boshqa foydalanuvchi o'rtasidagi xabarlar
        return chatRepository.findBySenderAndReceiver(user, otherUser);
    }
}

