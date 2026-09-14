package ro.hubstudentesc.dto.socialmedia;

import java.time.LocalDateTime;

public record FeedPaginationDto(
        LocalDateTime nextCursor,
        boolean hasMore
) {
}
