package jamol.socialmedia.dto;

import java.time.LocalDateTime;

public record CommentDTO(
        Long id,
        Long postId,
        Long userId,
        String content,
        LocalDateTime createdAt
) {
}
