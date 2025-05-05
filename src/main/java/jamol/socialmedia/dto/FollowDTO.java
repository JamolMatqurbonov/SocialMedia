    package jamol.socialmedia.dto;

    import java.time.LocalDateTime;

    public record FollowDTO(
            Long id,
            Long followerId,
            Long followingId,
            LocalDateTime createdAt
    ) {
    }
