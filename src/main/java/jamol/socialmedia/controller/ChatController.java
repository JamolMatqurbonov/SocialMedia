package jamol.socialmedia.controller;

import jamol.socialmedia.entity.Chat;
import jamol.socialmedia.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * Yangi xabar yuborish
     */
    @PostMapping("/send")
    public ResponseDTO sendMessage(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam String message
    ) {
        return chatService.sendMessage(senderId, receiverId, message);
    }

    /**
     * Foydalanuvchining barcha xabarlarini olish
     */
    @GetMapping("/user/{userId}")
    public List<Chat> getMessages(@PathVariable Long userId) {
        return chatService.getMessages(userId);
    }

    /**
     * Ikki foydalanuvchi o‘rtasidagi chat tarixini olish
     */
    @GetMapping("/history")
    public List<Chat> getChatHistory(
            @RequestParam Long userId,
            @RequestParam Long otherUserId
    ) {
        return chatService.getChatHistory(userId, otherUserId);
    }
}
