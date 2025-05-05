package jamol.socialmedia.repository;

import jamol.socialmedia.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Post ID bo'yicha barcha kommentlarni olish
    List<Comment> findByPostId(Long postId);
}
