package ro.hubstudentesc.dto.socialmedia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCommentRecordDto(
        @NotBlank
        @Size(max = 500)
        String content
) {
}
