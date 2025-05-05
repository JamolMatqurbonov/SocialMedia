package jamol.socialmedia.dto;

public record MessageRequestDTO(
        Long senderId,
        Long receiverId,
        String content
) {
}
