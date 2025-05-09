package jamol.socialmedia.service;

import io.minio.errors.MinioException;
import jamol.socialmedia.dto.PostCreateRequest;
import jamol.socialmedia.dto.PostDTO;
import jamol.socialmedia.entity.Post;
import jamol.socialmedia.entity.User;
import jamol.socialmedia.exception.PostNotFoundException;
import jamol.socialmedia.exception.UserNotFoundException;
import jamol.socialmedia.repository.PostRepository;
import jamol.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Transactional
    public PostDTO createPost(PostCreateRequest request) throws IOException, MinioException {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("Foydalanuvchi topilmadi"));

        String imageUrl = null;
        MultipartFile image = request.image();
        if (image != null && !image.isEmpty()) {
            imageUrl = s3Service.getFileUrl(s3Service.uploadFile(image));
        }

        Post post = Post.builder()
                .user(user)
                .content(request.content())
                .mediaUrl(imageUrl)
                .viewCount(0)
                .shareCount(0)
                .likedByUsers(new HashSet<>())
                .comments(new ArrayList<>())
                .build();

        post = postRepository.save(post);

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

    @Transactional
    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post topilmadi"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Foydalanuvchi topilmadi"));

        if (post.getLikedByUsers().contains(user)) {
            post.getLikedByUsers().remove(user);
        } else {
            post.getLikedByUsers().add(user);
        }

        postRepository.save(post);
    }

    @Transactional
    public void incrementViewCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post topilmadi"));

        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }

    @Transactional
    public void sharePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post topilmadi"));

        post.setShareCount(post.getShareCount() + 1);
        postRepository.save(post);
    }
}