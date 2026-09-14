package ro.hubstudentesc.web.socialmedia;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.socialmedia.PostCommentCreateResponseDto;
import ro.hubstudentesc.dto.socialmedia.PostCommentRecordDto;
import ro.hubstudentesc.dto.socialmedia.PostCommentResponseDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.service.socialmedia.PostCommentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed/posts")
public class PostCommentController {

    private final PostCommentService postCommentService;

    /**
     * Returnează toate comentariile unei postări.
     */
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<PostCommentResponseDto>> getComments(
            @PathVariable UUID postId
    ) {
        return ResponseEntity.ok(
                postCommentService.getComments(postId)
        );
    }

    /**
     * Adaugă un comentariu.
     *
     * Utilizatorul este identificat din JWT.
     */
    @PostMapping("/{postId}/comments")
    public ResponseEntity<PostCommentCreateResponseDto> addComment(
            Authentication authentication,
            @PathVariable UUID postId,
            @RequestBody @Valid PostCommentRecordDto dto
    ) {
        User user = (User) authentication.getPrincipal();

        PostCommentCreateResponseDto response =
                postCommentService.addComment(
                        postId,
                        user.getId(),
                        dto
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}