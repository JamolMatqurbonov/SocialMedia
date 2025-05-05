package jamol.socialmedia.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    // HTTP xavfsizlik sozlamalari
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Auth endpointlar uchun ruxsat
                        .anyRequest().authenticated() // Qolgan barcha so‘rovlar uchun autentifikatsiya talab qilinadi
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Har bir so‘rov mustaqil
                .authenticationProvider(authenticationProvider) // AuthenticationProvider qo'shish
                .csrf(AbstractHttpConfigurer::disable) // CSRF himoyasini o'chirish (REST API uchun)
                .cors(AbstractHttpConfigurer::disable); // CORS sozlamalari

        return httpSecurity.build();
    }

    // AuthenticationManager beanini yaratish
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
