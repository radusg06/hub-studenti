package ro.hubstudentesc.dto.socialmedia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;

import java.time.LocalDateTime;

public record PostRecordDto(
        @NotNull PostType type,

        @Size(max = 100)
        String title,

        @NotBlank
        @Size(max = 5000)
        String content,

        String[] mediaUrls,

        LocalDateTime eventDate,

        String location
) {}