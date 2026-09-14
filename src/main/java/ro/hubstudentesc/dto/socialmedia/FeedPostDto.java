package ro.hubstudentesc.dto.socialmedia;

import ro.hubstudentesc.enums.socialmediaEnums.PostType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record FeedPostDto(
        UUID id,
        FeedAuthorDto author,
        PostType type,
        String title,
        String content,
        List<String> mediaUrls,
        LocalDateTime eventDate,
        String location,
        Integer likesCount,
        Integer commentsCount,
        boolean hasLiked,
        LocalDateTime createdAt
) {
}