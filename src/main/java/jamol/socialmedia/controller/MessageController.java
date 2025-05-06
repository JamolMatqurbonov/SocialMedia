package jamol.socialmedia.controller;

import jamol.socialmedia.dto.MessageRequestDTO;
import jamol.socialmedia.dto.MessageResponseDTO;
import jamol.socialmedia.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // Xabar yuborish
    @PostMapping("/send")
    public ResponseEntity<MessageResponseDTO> sendMessage(@RequestBody MessageRequestDTO messageRequestDTO) {
        MessageResponseDTO responseDTO = messageService.sendMessage(messageRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
