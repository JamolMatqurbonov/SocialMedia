package jamol.socialmedia;



import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.stereotype.Component;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Foydalanuvchidan kelgan xabarni olish
        String clientMessage = message.getPayload();

        // Xabarni qaytarish (yoki boshqa foydalanuvchilarga yuborish)
        session.sendMessage(new TextMessage("Xabar qabul qilindi: " + clientMessage));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // WebSocket ulanishi o'rnatilgandan keyin bajariladigan kod
        System.out.println("Yangi ulanish: " + session.getId());
    }
}
