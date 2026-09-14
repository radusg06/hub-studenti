package ro.hubstudentesc.dto.faculties;

import java.util.UUID;

public record ReviewCreateResponseDto(
        boolean success,
        String message,
        UUID reviewId
) {
}
