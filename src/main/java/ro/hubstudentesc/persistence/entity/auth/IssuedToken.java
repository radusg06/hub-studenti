package ro.hubstudentesc.persistence.entity.auth;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import ro.hubstudentesc.enums.authEnums.GrantType;
import ro.hubstudentesc.enums.authEnums.RevocationReason;
import ro.hubstudentesc.enums.authEnums.TokenType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "issued_tokens")
public class IssuedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 255)
    private String jti;

    @Column(nullable = false, unique = true)
    private byte[] tokenHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GrantType grantType;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false, length = 100)
    private String clientId = "app_client";

    @Column(name = "session_id", nullable = false)
    private UUID sessionId;

    @Column(name = "refresh_token_id", nullable = false)
    private UUID refreshTokenId;

    @ElementCollection
    private List<String> scopes;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column
    private LocalDateTime revokedAt;

    @Enumerated(EnumType.STRING)
    @Column
    private RevocationReason revocationReason;

    public UUID getId(){return id;}
    public void setId(UUID id){this.id=id;}

    public String getJti(){return jti;}
    public void setJti(String jti){this.jti=jti;}

    public byte[] getTokenHash(){return tokenHash;}
    public void setTokenHash(byte[] tokenHash){this.tokenHash=tokenHash;}

    public TokenType getType(){return type;}
    public void setType(TokenType type){this.type=type;}

    public GrantType getGrantType(){return grantType;}
    public void setGrantType(GrantType grantType){this.grantType=grantType;}

    public UUID getUserId(){return userId;}
    public void setUserId(UUID userId){this.userId=userId;}

    public String getClientId(){return clientId;}
    public void setClientId(String clientId){this.clientId=clientId;}

    public UUID getSessionId(){return sessionId;}
    public void setSessionId(UUID sessionId){this.sessionId=sessionId;}

    public UUID getRefreshTokenId(){return refreshTokenId;}
    public void setRefreshTokenId(UUID refreshTokenId){this.refreshTokenId=refreshTokenId;}

    public List<String> getScopes(){return scopes;}
    public void setScopes(List<String> scopes){this.scopes=scopes;}

    public LocalDateTime getIssuedAt(){return issuedAt;}
    public void setIssuedAt(LocalDateTime issuedAt){this.issuedAt=issuedAt;}

    public LocalDateTime getExpiresAt(){return expiresAt;}
    public void setExpiresAt(LocalDateTime expiresAt){this.expiresAt=expiresAt;}

    public LocalDateTime getRevokedAt(){return revokedAt;}
    public void setRevokedAt(LocalDateTime revokedAt){this.revokedAt=revokedAt;}

    public RevocationReason getRevocationReason(){return revocationReason;}
    public void setRevocationReason(RevocationReason revocationReason){this.revocationReason=revocationReason;}
}