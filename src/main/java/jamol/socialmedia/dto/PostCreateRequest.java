package jamol.socialmedia.dto;

import org.springframework.web.multipart.MultipartFile;

public record PostCreateRequest(
        Long userId,
        String content,
        MultipartFile image
) {
}
