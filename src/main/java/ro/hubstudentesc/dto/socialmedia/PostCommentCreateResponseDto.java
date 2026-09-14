package ro.hubstudentesc.dto.socialmedia;

public record PostCommentCreateResponseDto(
        boolean success,
        PostCommentResponseDto comment
) {
}
