package ro.hubstudentesc.persistence.entity.auth;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import ro.hubstudentesc.enums.authEnums.RevocationReason;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "auth_sessions")
public class AuthSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(nullable = false , unique = true)
    private byte[] sessionTokenHash ;

    @Column(nullable = false)
    private LocalDateTime authTime;

    @Column(nullable = false , length = 255)
    @NotBlank
    private String ipAddress;

    @Column(nullable = false , length = 255)
    @NotBlank
    private String userAgent;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private LocalDateTime lastSeenAt;

    @Column
    private LocalDateTime revokedAt;

    @Enumerated(EnumType.STRING)
    @Column
    private RevocationReason revocationReason;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public UUID getId(){return id;}
    public void setId(UUID id){this.id=id;}

    public UUID getUserId(){return userId;}
    public void setUserId(UUID userId){this.userId=userId;}

    public byte[] getSessionTokenHash(){return sessionTokenHash;}
    public void setSessionTokenHash(byte[] sessionTokenHash){this.sessionTokenHash=sessionTokenHash;}

    public LocalDateTime getAuthTime(){return authTime;}
    public void setAuthTime(LocalDateTime authTime){this.authTime=authTime;}

    public String getIpAddress(){return ipAddress;}
    public void setIpAddress(String ipAddress){this.ipAddress=ipAddress;}

    public String getUserAgent(){return userAgent;}
    public void setUserAgent(String userAgent){this.userAgent=userAgent;}

    public LocalDateTime getExpiresAt(){return expiresAt;}
    public void setExpiresAt(LocalDateTime expiresAt){this.expiresAt=expiresAt;}

    public LocalDateTime getLastSeenAt(){return lastSeenAt;}
    public void setLastSeenAt(LocalDateTime lastSeenAt){this.lastSeenAt=lastSeenAt;}

    public LocalDateTime getRevokedAt(){return revokedAt;}
    public void setRevokedAt(LocalDateTime revokedAt){this.revokedAt=revokedAt;}

    public RevocationReason getRevocationReason(){return revocationReason;}
    public void setRevocationReason(RevocationReason revocationReason){this.revocationReason=revocationReason;}

    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
}
