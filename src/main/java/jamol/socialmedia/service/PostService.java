package jamol.socialmedia.service;

import io.minio.errors.MinioException;
import jamol.socialmedia.dto.PostCreateRequest;
import jamol.socialmedia.dto.PostDTO;
import jamol.socialmedia.entity.Post;
import jamol.socialmedia.entity.User;
import jamol.socialmedia.repository.PostRepository;
import jamol.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    /**
     *  Yangi post yaratish xizmati.
     */
    public PostDTO createPost(PostCreateRequest request) throws IOException, MinioException {
        // Foydalanuvchini tekshiramiz
        User user = (User) userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        // Rasm yuklangan bo‘lsa, S3'ga joylaymiz
        String imageUrl = null;
        MultipartFile image = request.image();
        if (image != null && !image.isEmpty()) {
            // Rasmni yuklash va URL olish
            imageUrl = s3Service.uploadFile(image);
        }

        // Post obyektini yig'amiz
        Post post = Post.builder()
                .user(user)
                .content(request.content())
                .mediaUrl(imageUrl)
                .viewCount(0)
                .shareCount(0)
                .likedByUsers(new HashSet<>())
                .comments(List.of())
                .build();

        post = postRepository.save(post);

        // DTO shaklida qaytaramiz
        return new PostDTO(
                post.getId(),
                user.getId(),
                post.getContent(),
                post.getMediaUrl(),
                post.getLikedByUsers().size(),
                post.getComments().size(),
                post.getViewCount(),
                post.getShareCount(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    /**
     *  Postga like bosish (yoki oldingi like'ni bekor qilish).
     */
    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post topilmadi"));
        User user = (User) userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        // Like toggle logikasi
        if (post.getLikedByUsers().contains(user)) {
            post.getLikedByUsers().remove(user); // unlike
        } else {
            post.getLikedByUsers().add(user);    // like
        }

        postRepository.save(post);
    }

    /**
     *  Post ko‘rishlar sonini oshirish.
     */
    public void incrementViewCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post topilmadi"));

        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }

    /**
     * Postni ulashish (share) funksiyasi.
     */
    public void sharePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post topilmadi"));

        post.setShareCount(post.getShareCount() + 1);
        postRepository.save(post);
    }
}
