package jamol.socialmedia.repository;

import jamol.socialmedia.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
    Long countByUserId(Long userId);
}
