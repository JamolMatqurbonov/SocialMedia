package jamol.socialmedia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;  // Kuzatuvchi foydalanuvchi

    @ManyToOne
    @JoinColumn(name = "followee_id")
    private User followee;  // Kuzatilayotgan foydalanuvchi

    private LocalDateTime followedAt;  // Kuzatish vaqti

    // Getters va Setters
}
