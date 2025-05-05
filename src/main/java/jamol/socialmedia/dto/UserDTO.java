package jamol.socialmedia.dto;

import jamol.socialmedia.entity.Role;

public record UserDTO(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email,
        String profilePictureUrl,
        Role role) {
}
