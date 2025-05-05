package jamol.socialmedia.repository;

import jamol.socialmedia.entity.Reward;
import jamol.socialmedia.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RewardRepository extends CrudRepository<Reward, Integer> {
    List<Reward> findByUser(User user);
}
