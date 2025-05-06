package jamol.socialmedia.dto;

public record UserDTO(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email,
        String profilePictureUrl,
        String role) {
}
