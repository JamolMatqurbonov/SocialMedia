package jamol.socialmedia.dto;

import java.time.LocalDateTime;

public record LikeDTO(
        Long id,
        Long postId,
        Long userId,
        LocalDateTime createdAt
) {
}
