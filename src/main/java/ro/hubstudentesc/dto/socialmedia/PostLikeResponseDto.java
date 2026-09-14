package ro.hubstudentesc.dto.socialmedia;

public record PostLikeResponseDto(
        boolean success,
        boolean liked,
        int totalLikes
) {
}
