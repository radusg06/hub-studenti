package ro.hubstudentesc.mapper.auth;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.auth.RefreshTokenRecordDto;
import ro.hubstudentesc.enums.authEnums.RevocationReason;
import ro.hubstudentesc.persistence.entity.auth.RefreshToken;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class RefreshTokenMapperImpl implements RefreshTokenMapper {

    @Override
    public RefreshTokenRecordDto toDto(RefreshToken refreshToken) {
        if ( refreshToken == null ) {
            return null;
        }

        UUID id = null;
        UUID userId = null;
        String clientId = null;
        UUID sessionId = null;
        List<String> scopes = null;
        LocalDateTime authTime = null;
        UUID familyId = null;
        UUID parentId = null;
        UUID replacedById = null;
        LocalDateTime issuedAt = null;
        LocalDateTime expiresAt = null;
        LocalDateTime lastUsedAt = null;
        Integer useCount = null;
        LocalDateTime revokedAt = null;
        RevocationReason revocationReason = null;

        id = refreshToken.getId();
        userId = refreshToken.getUserId();
        clientId = refreshToken.getClientId();
        sessionId = refreshToken.getSessionId();
        List<String> list = refreshToken.getScopes();
        if ( list != null ) {
            scopes = new ArrayList<String>( list );
        }
        authTime = refreshToken.getAuthTime();
        familyId = refreshToken.getFamilyId();
        parentId = refreshToken.getParentId();
        replacedById = refreshToken.getReplacedById();
        issuedAt = refreshToken.getIssuedAt();
        expiresAt = refreshToken.getExpiresAt();
        lastUsedAt = refreshToken.getLastUsedAt();
        useCount = refreshToken.getUseCount();
        revokedAt = refreshToken.getRevokedAt();
        revocationReason = refreshToken.getRevocationReason();

        RefreshTokenRecordDto refreshTokenRecordDto = new RefreshTokenRecordDto( id, userId, clientId, sessionId, scopes, authTime, familyId, parentId, replacedById, issuedAt, expiresAt, lastUsedAt, useCount, revokedAt, revocationReason );

        return refreshTokenRecordDto;
    }
}
