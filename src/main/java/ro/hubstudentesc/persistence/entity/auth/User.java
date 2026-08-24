package ro.hubstudentesc.persistence.entity.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NoArgsConstructor;
import ro.hubstudentesc.enums.authEnums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank
    private String sub;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String givenName;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String familyName;

    @Column(length = 500)
    private String picture;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Column(length = 30)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean phoneNumberVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private Boolean isVerified = false;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column
    private LocalDateTime lockedUntil;

    @Column(nullable = false)
    @PositiveOrZero
    private Integer failedLoginAttempts = 0;

    @Column
    private LocalDateTime lastLoginAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public String getSub() {return sub;}
    public void setSub(String sub) {this.sub = sub;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getGivenName() {return givenName;}
    public void setGivenName(String givenName) {this.givenName = givenName;}

    public String getFamilyName() {return familyName;}
    public void setFamilyName(String familyName) {this.familyName = familyName;}

    public String getPicture() {return picture;}
    public void setPicture(String picture) {this.picture = picture;}

    public Boolean getEmailVerified() {return emailVerified;}
    public void setEmailVerified(Boolean emailVerified) {this.emailVerified = emailVerified;}

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public Boolean getPhoneNumberVerified() {return phoneNumberVerified;}
    public void setPhoneNumberVerified(Boolean phoneNumberVerified) {this.phoneNumberVerified = phoneNumberVerified;}

    public UserRole getRole() {return role;}
    public void setRole(UserRole role) {this.role = role;}

    public Boolean getIsVerified() {return isVerified;}
    public void setIsVerified(Boolean isVerified) {this.isVerified = isVerified;}

    public Boolean getIsActive() {return isActive;}
    public void setIsActive(Boolean isActive) {this.isActive = isActive;}

    public LocalDateTime getLockedUntil() {return lockedUntil;}
    public void setLockedUntil(LocalDateTime lockedUntil) {this.lockedUntil = lockedUntil;}

    public Integer getFailedLoginAttempts() {return failedLoginAttempts;}
    public void setFailedLoginAttempts(Integer failedLoginAttempts) {this.failedLoginAttempts = failedLoginAttempts;}

    public LocalDateTime getLastLoginAt() {return lastLoginAt;}
    public void setLastLoginAt(LocalDateTime lastLoginAt) {this.lastLoginAt = lastLoginAt;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}