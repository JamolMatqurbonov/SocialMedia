package jamol.socialmedia.dto;

import java.time.LocalDateTime;

public record MessageResponseDTO(
        Long id,
        String content,
        Long senderId,
        Long receiverId,
        LocalDateTime sentAt
) {
}
