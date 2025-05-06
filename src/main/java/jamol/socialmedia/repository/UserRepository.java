package jamol.socialmedia.repository;

import jamol.socialmedia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Optional<User> qaytadi, shunda orElseThrow ishlaydi
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);  // Username borligini tekshiradi

    Optional<User> findById(Long userId);

    Optional<Object> findByEmail(String username);

    boolean existsByEmail(String email);
}
