package ro.hubstudentesc.dto.socialmedia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostRecordDto(
        @NotNull
        UUID userId,

        @NotBlank
        @Size(max = 100)
        String title,

        @NotBlank
        @Size(max = 5000)
        String content,

        @NotNull
        PostType type,

        @NotNull
        LocalDateTime createdAt
) {
}
