package jamol.socialmedia.repository;

import jamol.socialmedia.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepositroy extends JpaRepository<Like, Integer> {
}
