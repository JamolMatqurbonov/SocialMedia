package jamol.socialmedia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Clientlardan kelgan xabarlarni boshqarish uchun
        registry.enableSimpleBroker("/topic");  // Clientlarga yuborish uchun "/topic"ni ishlatish
        registry.setApplicationDestinationPrefixes("/app");  // Serverga yuboriladigan xabarlar
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket ulanish nuqtasi
        registry.addEndpoint("/ws").withSockJS();  // "/ws" WebSocket endpointini yaratadi
    }
}
