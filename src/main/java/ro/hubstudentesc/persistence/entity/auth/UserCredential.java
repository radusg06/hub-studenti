package ro.hubstudentesc.persistence.entity.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "user_credentials")
public class UserCredential {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(nullable = false , length = 255)
    @NotBlank
    private String passwordHash;

    @Column(nullable = false , length = 50)
    @NotBlank
    private String algorithm = "argon2id";

    @Column(nullable = false )
    @PositiveOrZero
    private Integer passwordVersion = 1;

    @Column(nullable = false )
    private Boolean mustChange = false;

    @Column
    private LocalDateTime passwordChangedAt;

    @Column(nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false )
    private LocalDateTime updatedAt;

    public UUID getUserId(){return userId;}
    public void setUserId(UUID userId){this.userId=userId;}

    public String getPasswordHash(){return passwordHash;}
    public void setPasswordHash(String passwordHash){this.passwordHash=passwordHash;}

    public String getAlgorithm(){return algorithm;}
    public void setAlgorithm(String algorithm){this.algorithm=algorithm;}

    public Integer getPasswordVersion(){return passwordVersion;}
    public void setPasswordVersion(Integer passwordVersion){this.passwordVersion=passwordVersion;}

    public Boolean getMustChange(){return mustChange;}
    public void setMustChange(Boolean mustChange){this.mustChange=mustChange;}

    public LocalDateTime getPasswordChangedAt(){return passwordChangedAt;}
    public void setPasswordChangedAt(LocalDateTime passwordChangedAt){this.passwordChangedAt=passwordChangedAt;}

    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}

    public LocalDateTime getUpdatedAt(){return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt){this.updatedAt=updatedAt;}

}