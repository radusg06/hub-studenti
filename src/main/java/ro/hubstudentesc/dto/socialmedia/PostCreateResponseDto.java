package ro.hubstudentesc.dto.socialmedia;

import java.util.UUID;

public record PostCreateResponseDto(
        boolean success,
        String message,
        UUID postId
) {
}
