package jamol.socialmedia.dto;

public record JwtResponseDTO(
        String token,
        String bearer, Long id, String username, String email) {
}
