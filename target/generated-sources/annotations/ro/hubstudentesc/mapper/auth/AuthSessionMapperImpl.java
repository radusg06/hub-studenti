package ro.hubstudentesc.mapper.auth;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.auth.AuthSessionRecordDto;
import ro.hubstudentesc.enums.authEnums.RevocationReason;
import ro.hubstudentesc.persistence.entity.auth.AuthSession;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class AuthSessionMapperImpl implements AuthSessionMapper {

    @Override
    public AuthSessionRecordDto toDto(AuthSession authSession) {
        if ( authSession == null ) {
            return null;
        }

        UUID id = null;
        UUID userId = null;
        LocalDateTime authTime = null;
        String ipAddress = null;
        String userAgent = null;
        LocalDateTime expiresAt = null;
        LocalDateTime lastSeenAt = null;
        LocalDateTime revokedAt = null;
        RevocationReason revocationReason = null;
        LocalDateTime createdAt = null;

        id = authSession.getId();
        userId = authSession.getUserId();
        authTime = authSession.getAuthTime();
        ipAddress = authSession.getIpAddress();
        userAgent = authSession.getUserAgent();
        expiresAt = authSession.getExpiresAt();
        lastSeenAt = authSession.getLastSeenAt();
        revokedAt = authSession.getRevokedAt();
        revocationReason = authSession.getRevocationReason();
        createdAt = authSession.getCreatedAt();

        AuthSessionRecordDto authSessionRecordDto = new AuthSessionRecordDto( id, userId, authTime, ipAddress, userAgent, expiresAt, lastSeenAt, revokedAt, revocationReason, createdAt );

        return authSessionRecordDto;
    }
}
