package jamol.socialmedia.service;

import jamol.socialmedia.dto.CommentDTO;
import jamol.socialmedia.entity.Comment;
import jamol.socialmedia.repository.CommentRepository;
import jamol.socialmedia.repository.PostRepository;
import jamol.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // Yangi komment yaratish
    public CommentDTO createComment(Long postId, Long userId, String content) {
        // Post va Foydalanuvchini tekshiramiz
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post topilmadi"));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        // Yangi kommentni yaratamiz
        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(content)
                .build();

        comment = commentRepository.save(comment);

        // DTO shaklida qaytaramiz
        return mapToDTO(comment);
    }

    // Kommentni olish
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        // Kommentlarni to'g'ri olish
        List<Comment> comments = commentRepository.findByPostId(postId);

        // Kommentlarni DTO shaklida qaytarish
        return comments.stream()
                .map(this::mapToDTO)  // To'g'ri ishlatish
                .collect(Collectors.toList());
    }

    // Entity-ni DTO ga aylantirish
    private CommentDTO mapToDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getPost().getId(),
                comment.getUser().getId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}

