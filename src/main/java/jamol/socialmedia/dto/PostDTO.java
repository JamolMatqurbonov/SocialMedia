package jamol.socialmedia.dto;

import java.time.LocalDateTime;

public record PostDTO(
        Long id,
        Long userId,
        String content,
        String imageUrl,
        int likeCount,
        int commentCount,
        int viewsCount,
        int sharesCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
