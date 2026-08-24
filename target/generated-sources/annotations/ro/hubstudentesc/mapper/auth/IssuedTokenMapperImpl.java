package ro.hubstudentesc.mapper.auth;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.auth.IssuedTokenRecordDto;
import ro.hubstudentesc.enums.authEnums.GrantType;
import ro.hubstudentesc.enums.authEnums.RevocationReason;
import ro.hubstudentesc.enums.authEnums.TokenType;
import ro.hubstudentesc.persistence.entity.auth.IssuedToken;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class IssuedTokenMapperImpl implements IssuedTokenMapper {

    @Override
    public IssuedTokenRecordDto toDto(IssuedToken issuedToken) {
        if ( issuedToken == null ) {
            return null;
        }

        UUID id = null;
        String jti = null;
        TokenType type = null;
        GrantType grantType = null;
        UUID userId = null;
        String clientId = null;
        UUID sessionId = null;
        UUID refreshTokenId = null;
        List<String> scopes = null;
        LocalDateTime issuedAt = null;
        LocalDateTime expiresAt = null;
        LocalDateTime revokedAt = null;
        RevocationReason revocationReason = null;

        id = issuedToken.getId();
        jti = issuedToken.getJti();
        type = issuedToken.getType();
        grantType = issuedToken.getGrantType();
        userId = issuedToken.getUserId();
        clientId = issuedToken.getClientId();
        sessionId = issuedToken.getSessionId();
        refreshTokenId = issuedToken.getRefreshTokenId();
        List<String> list = issuedToken.getScopes();
        if ( list != null ) {
            scopes = new ArrayList<String>( list );
        }
        issuedAt = issuedToken.getIssuedAt();
        expiresAt = issuedToken.getExpiresAt();
        revokedAt = issuedToken.getRevokedAt();
        revocationReason = issuedToken.getRevocationReason();

        IssuedTokenRecordDto issuedTokenRecordDto = new IssuedTokenRecordDto( id, jti, type, grantType, userId, clientId, sessionId, refreshTokenId, scopes, issuedAt, expiresAt, revokedAt, revocationReason );

        return issuedTokenRecordDto;
    }
}
