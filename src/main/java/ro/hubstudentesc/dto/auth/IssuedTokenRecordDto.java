package ro.hubstudentesc.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import ro.hubstudentesc.enums.authEnums.GrantType;
import ro.hubstudentesc.enums.authEnums.RevocationReason;
import ro.hubstudentesc.enums.authEnums.TokenType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record IssuedTokenRecordDto(
        UUID id,

        @NotBlank
        @Size(max = 255)
        String jti,

        TokenType type,

        GrantType grantType,

        UUID userId,

        @NotBlank
        @Size(max = 255)
        String clientId,

        UUID sessionId,

        UUID refreshTokenId,

        @NotEmpty
        List<String> scopes,

        LocalDateTime issuedAt,

        LocalDateTime expiresAt,

        LocalDateTime revokedAt,

        RevocationReason revocationReason
) {
}