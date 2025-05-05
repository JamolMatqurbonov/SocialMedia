package jamol.socialmedia.service;

import jamol.socialmedia.dto.MessageRequestDTO;
import jamol.socialmedia.dto.MessageResponseDTO;
import jamol.socialmedia.entity.Message;
import jamol.socialmedia.entity.User;
import jamol.socialmedia.repository.MessageRepository;
import jamol.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    // Xabar yuborish
    public MessageResponseDTO sendMessage(MessageRequestDTO dto) {
        User sender = userRepository.findById(dto.senderId())
                .orElseThrow(() -> new RuntimeException("Yuboruvchi topilmadi"));
        User receiver = userRepository.findById(dto.receiverId())
                .orElseThrow(() -> new RuntimeException("Qabul qiluvchi topilmadi"));

        Message message = Message.builder()
                .content(dto.content())
                .sentAt(LocalDateTime.now())
                .sender(sender)
                .receiver(receiver)
                .build();

        messageRepository.save(message);

        return mapToDTO(message);
    }

    // Foydalanuvchining yuborgan yoki olgan xabarlari
    public List<MessageResponseDTO> getMessages(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        List<Message> messages = messageRepository.findBySenderOrReceiver(user, user);

        return messages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // DTOga aylantirish
    private MessageResponseDTO mapToDTO(Message message) {
        return new MessageResponseDTO(
                message.getId(),
                message.getContent(),
                message.getSender().getId(),
                message.getReceiver().getId(),
                message.getSentAt()
        );
    }
}
