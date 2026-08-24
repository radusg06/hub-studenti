package ro.hubstudentesc.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ro.hubstudentesc.enums.authEnums.RevocationReason;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuthSessionRecordDto(
        UUID id,

        UUID userId,

        LocalDateTime authTime,

        @NotBlank
        @Size(max=255)
        String ipAddress,

        @NotBlank
        @Size(max=255)
        String userAgent,

        LocalDateTime expiresAt,

        LocalDateTime lastSeenAt,

        LocalDateTime revokedAt,

        RevocationReason revocationReason,

        LocalDateTime createdAt
) {
}
