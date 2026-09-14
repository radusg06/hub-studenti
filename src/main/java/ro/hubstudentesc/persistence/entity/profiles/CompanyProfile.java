package ro.hubstudentesc.persistence.entity.profiles;

import jakarta.persistence.*;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "company_profiles", schema = "profiles")
public class CompanyProfile {

    @Id
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String companyName;

    @Column(unique = true)
    private String cuiCif;

    private String industry;
    private String description;
    private String websiteUrl;
    private String logoUrl;

    @Column(nullable = false)
    private String city = "Bucuresti";

    private String address;
    private String contactPhone;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public UUID getUserId() {return userId;}
    public void setUserId(UUID userId) {this.userId = userId;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public String getCompanyName() {return companyName;}
    public void setCompanyName(String companyName) {this.companyName = companyName;}

    public String getCuiCif() {return cuiCif;}
    public void setCuiCif(String cuiCif) {this.cuiCif = cuiCif;}

    public String getIndustry() {return industry;}
    public void setIndustry(String industry) {this.industry = industry;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getWebsiteUrl() {return websiteUrl;}
    public void setWebsiteUrl(String websiteUrl) {this.websiteUrl = websiteUrl;}

    public String getLogoUrl() {return logoUrl;}
    public void setLogoUrl(String logoUrl) {this.logoUrl = logoUrl;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public String getContactPhone() {return contactPhone;}
    public void setContactPhone(String contactPhone) {this.contactPhone = contactPhone;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
