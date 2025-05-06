package jamol.socialmedia.service;

import jamol.socialmedia.entity.User;
import jamol.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username orqali foydalanuvchini topish
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Foydalanuvchi topilmadi: " + username));
    }
}
