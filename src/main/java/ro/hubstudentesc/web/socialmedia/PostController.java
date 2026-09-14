package ro.hubstudentesc.web.socialmedia;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.hubstudentesc.dto.socialmedia.FeedResponseDto;
import ro.hubstudentesc.dto.socialmedia.PostCreateResponseDto;
import ro.hubstudentesc.dto.socialmedia.PostRecordDto;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.service.socialmedia.MediaStorageService;
import ro.hubstudentesc.service.socialmedia.PostService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/feed")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MediaStorageService mediaStorageService;

    @GetMapping
    public ResponseEntity<FeedResponseDto> getFeed(
            Authentication authentication,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String type
    ) {
        User user = (User) authentication.getPrincipal();

        PostType postType = null;

        if (type != null && !type.equalsIgnoreCase("ALL")) {
            postType = PostType.valueOf(type.toUpperCase());
        }

        return ResponseEntity.ok(
                postService.getFeed(
                        parseCursor(cursor),
                        limit,
                        user.getId(),
                        postType
                )
        );
    }

    private LocalDateTime parseCursor(String cursor) {
        if (cursor == null || cursor.isBlank()) {
            return null;
        }

        return OffsetDateTime.parse(cursor).toLocalDateTime();
    }

    @PostMapping("/posts")
    public ResponseEntity<PostCreateResponseDto> createPost(
            Authentication authentication,
            @RequestBody @Valid PostRecordDto dto
    ) {
        User user = (User) authentication.getPrincipal();

        UUID postId = postService.addPost(
                user.getId(),
                dto
        );

        PostCreateResponseDto response =
                new PostCreateResponseDto(
                        true,
                        "Postarea a fost publicata pe fluxul social",
                        postId
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/posts/media")
    public ResponseEntity<String> uploadMedia(
            @RequestParam("file") MultipartFile file
    ) {
        String url = mediaStorageService.upload(file);

        return ResponseEntity.ok(url);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<String> updatePost(
            Authentication authentication,
            @PathVariable UUID id,
            @RequestBody @Valid PostRecordDto dto
    ) {
        User user = (User) authentication.getPrincipal();

        postService.updatePost(
                id,
                user.getId(),
                dto
        );

        return ResponseEntity.ok(
                "Post updated successfully"
        );
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(
            Authentication authentication,
            @PathVariable UUID id
    ) {
        User user = (User) authentication.getPrincipal();

        postService.deletePost(
                id,
                user.getId()
        );

        return ResponseEntity.ok(
                "Post deleted successfully"
        );
    }
}
