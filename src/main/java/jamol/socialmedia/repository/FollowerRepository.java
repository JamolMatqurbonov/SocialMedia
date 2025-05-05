package jamol.socialmedia.repository;

import jamol.socialmedia.entity.Follower;
import jamol.socialmedia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    boolean existsByFollowerAndFollowee(User follower, User followee);
    Optional<Follower> findByFollowerAndFollowee(User follower, User followee);
    List<Follower> findByFollowee(User followee);
    List<Follower> findByFollower(User follower);
}
