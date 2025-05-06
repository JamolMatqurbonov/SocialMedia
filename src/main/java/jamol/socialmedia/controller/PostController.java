package jamol.socialmedia.controller;

import io.minio.errors.MinioException;
import jamol.socialmedia.dto.PostCreateRequest;
import jamol.socialmedia.dto.PostDTO;
import jamol.socialmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // Yangi post yaratish
    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost( @RequestBody PostCreateRequest request) throws IOException, MinioException {
        PostDTO postDTO = postService.createPost(request);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    // Postga like qo'shish (yoki bekor qilish)
    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId, @PathVariable Long userId) {
        postService.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }

    // Post ko'rishlar sonini oshirish
    @PostMapping("/{postId}/view")
    public ResponseEntity<Void> incrementViewCount(@PathVariable Long postId) {
        postService.incrementViewCount(postId);
        return ResponseEntity.ok().build();
    }

    // Postni ulashish (share)
    @PostMapping("/{postId}/share")
    public ResponseEntity<Void> sharePost(@PathVariable Long postId) {
        postService.sharePost(postId);
        return ResponseEntity.ok().build();
    }
}
