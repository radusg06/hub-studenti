package ro.hubstudentesc.persistence.entity.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ro.hubstudentesc.enums.authEnums.RevocationReason;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "refresh_tokens", schema = "app_auth")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private byte[] tokenHash;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false, length = 100)
    private String clientId = "app_client";

    @Column(name = "session_id")
    private UUID sessionId;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(nullable = false)
    private String[] scopes = new String[0];

    @Column(nullable = false)
    private UUID familyId;

    private UUID parentId;

    @Column(name = "replaced_by_id" , unique = true)
    private UUID replacedById;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column
    private LocalDateTime lastUsedAt;

    @Column(nullable = false)
    @PositiveOrZero
    private Integer useCount = 0;

    @Column
    private LocalDateTime revokedAt;

    @Enumerated(EnumType.STRING)
    @Column
    private RevocationReason revocationReason;

    @Column(nullable = false)
    private LocalDateTime authTime;

    public UUID getId(){return id;}
    public void setId(UUID id){this.id=id;}

    public byte[] getTokenHash(){return tokenHash;}
    public void setTokenHash(byte[] tokenHash){this.tokenHash=tokenHash;}

    public UUID getUserId(){return userId;}
    public void setUserId(UUID userId){this.userId=userId;}

    public String getClientId(){return clientId;}
    public void setClientId(String clientId){this.clientId=clientId;}

    public UUID getSessionId(){return sessionId;}
    public void setSessionId(UUID sessionId){this.sessionId=sessionId;}

    public String[] getScopes(){return scopes;}
    public void setScopes(String[] scopes){this.scopes=scopes;}

    public UUID getFamilyId(){return familyId;}
    public void setFamilyId(UUID familyId){this.familyId=familyId;}

    public UUID getParentId(){return parentId;}
    public void setParentId(UUID parentId){this.parentId=parentId;}

    public UUID getReplacedById(){return replacedById;}
    public void setReplacedById(UUID replacedById){this.replacedById = replacedById;}

    public LocalDateTime getIssuedAt(){return issuedAt;}
    public void setIssuedAt(LocalDateTime issuedAt){this.issuedAt=issuedAt;}

    public LocalDateTime getExpiresAt(){return expiresAt;}
    public void setExpiresAt(LocalDateTime expiresAt){this.expiresAt=expiresAt;}

    public LocalDateTime getLastUsedAt(){return lastUsedAt;}
    public void setLastUsedAt(LocalDateTime lastUsedAt){this.lastUsedAt=lastUsedAt;}

    public Integer getUseCount(){return useCount;}
    public void setUseCount(Integer useCount){this.useCount=useCount;}

    public LocalDateTime getRevokedAt(){return revokedAt;}
    public void setRevokedAt(LocalDateTime revokedAt){this.revokedAt=revokedAt;}

    public RevocationReason getRevocationReason(){return revocationReason;}
    public void setRevocationReason(RevocationReason revocationReason){this.revocationReason=revocationReason;}

    public LocalDateTime getAuthTime(){return authTime;}
    public void setAuthTime(LocalDateTime authTime){this.authTime=authTime;}
}
