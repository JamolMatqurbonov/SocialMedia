package jamol.socialmedia.controller;

import jamol.socialmedia.dto.CommentDTO;
import jamol.socialmedia.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Yangi komment yaratish
    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId,
                                                    @PathVariable Long userId,
                                                    @RequestParam String content) {
        try {
            CommentDTO commentDTO = commentService.createComment(postId, userId, content);
            return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Post ID bo‘yicha kommentlarni olish
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
