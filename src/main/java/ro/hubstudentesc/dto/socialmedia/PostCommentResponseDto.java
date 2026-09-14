package ro.hubstudentesc.dto.socialmedia;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostCommentResponseDto(
        UUID id,
        UUID postId,
        String content,
        String authorName,
        LocalDateTime createdAt
) {
}
