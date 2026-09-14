package ro.hubstudentesc.dto.socialmedia;

import java.util.List;

public record FeedResponseDto(
        List<FeedPostDto> data,
        FeedPaginationDto pagination
) {
}
