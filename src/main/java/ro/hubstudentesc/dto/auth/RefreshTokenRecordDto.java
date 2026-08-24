package ro.hubstudentesc.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import ro.hubstudentesc.enums.authEnums.RevocationReason;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RefreshTokenRecordDto(
        UUID id,

        UUID userId,

        @NotBlank
        @Size(max = 255)
        String clientId,

        UUID sessionId,

        @NotEmpty
        List<String> scopes,

        LocalDateTime authTime,

        UUID familyId,

        UUID parentId,

        UUID replacedById,

        LocalDateTime issuedAt,

        LocalDateTime expiresAt,

        LocalDateTime lastUsedAt,

        @PositiveOrZero
        Integer useCount,

        LocalDateTime revokedAt,

        RevocationReason revocationReason
) {
}