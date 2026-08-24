package ro.hubstudentesc.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCredentialRecordDto(
        UUID userId,

        @NotBlank
        String algorithm,

        @PositiveOrZero
        Integer passwordVersion,

        Boolean mustChange,

        LocalDateTime passwordChangedAt,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
