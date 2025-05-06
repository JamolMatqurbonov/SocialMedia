package jamol.socialmedia.dto;
public record RegisterDTO(
        String username,
        String password,    // ← this must match your JSON
        String email,
        String firstName,
        String lastName,
        String role
) {}


