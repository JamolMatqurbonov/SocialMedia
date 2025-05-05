package jamol.socialmedia.dto;

import jamol.socialmedia.entity.Role;

public record RegisterDTO(
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        Role role  // Enum sifatida keladi
) {}
