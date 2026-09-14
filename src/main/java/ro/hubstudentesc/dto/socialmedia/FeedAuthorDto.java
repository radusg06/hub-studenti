package ro.hubstudentesc.dto.socialmedia;

import java.util.UUID;

public record FeedAuthorDto(
        UUID id,
        String name,
        String avatarUrl
) {
}
