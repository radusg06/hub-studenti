package ro.hubstudentesc.web.socialmedia;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.socialmedia.PostLikeResponseDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.service.socialmedia.PostLikeService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/feed/posts")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<PostLikeResponseDto> toggleLike(
            Authentication authentication,
            @PathVariable UUID postId
    ) {
        User user = (User) authentication.getPrincipal();

        PostLikeResponseDto response =
                postLikeService.toggleLike(
                        postId,
                        user.getId()
                );

        return ResponseEntity.ok(response);
    }
}