package jamol.socialmedia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new jamol.socialmedia.WebSocketHandler(), "/chat")
                .addInterceptors(new HttpSessionHandshakeInterceptor())  // Sessiya boshqaruvi
                .setAllowedOrigins("*");  // Xavfsizlik uchun kerak bo'lishi mumkin
    }
}
