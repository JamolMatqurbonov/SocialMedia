package jamol.socialmedia.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String username;  // Bu login uchun

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email    ;

    private boolean blocked;

    private String profilePictureUrl;

    @Enumerated(EnumType.STRING)  // Enumdan foydalanish
    private Role role;  // Enum shaklida, ROLE_USER, ROLE_ADMIN va h.k.

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<User> following = new HashSet<>();

    // === UserDetails metodlari ===
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role.name());  // Enum ni stringga aylantiradi
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Doim true qaytaryapti
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;  // Agar blocked = true bo‘lsa, locked bo‘ladi
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Credentials hech qachon eskirmaydi
    }

    @Override
    public boolean isEnabled() {
        return !blocked;  // Agar blocked bo‘lmasa, foydalanuvchi faollashadi
    }
}
