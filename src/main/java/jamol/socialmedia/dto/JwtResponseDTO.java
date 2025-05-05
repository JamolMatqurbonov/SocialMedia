package jamol.socialmedia.dto;

public record JwtResponseDTO(
        String token,
        String type,
        Long id,
        String username,
        String email
) {
}
