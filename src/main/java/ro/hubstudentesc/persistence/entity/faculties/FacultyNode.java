package ro.hubstudentesc.persistence.entity.faculties;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "faculty_nodes", schema = "faculties")
public class FacultyNode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String universityName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String city;

    private String admissionInfo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tuitionFeeAnnual;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(nullable = false)
    private String[] studyLevels;

    private String websiteUrl;

    private String brochureUrl;

    private String logoUrl;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal averageRating;

    @Column(nullable = false)
    private Integer reviewsCount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public String getUniversityName() {return universityName;}
    public void setUniversityName(String universityName) {this.universityName = universityName;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getSlug() {return slug;}
    public void setSlug(String slug) {this.slug = slug;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public String getAdmissionInfo() {return admissionInfo;}
    public void setAdmissionInfo(String admissionInfo) {this.admissionInfo = admissionInfo;}

    public BigDecimal getTuitionFeeAnnual() {return tuitionFeeAnnual;}
    public void setTuitionFeeAnnual(BigDecimal tuitionFeeAnnual) {this.tuitionFeeAnnual = tuitionFeeAnnual;}

    public String[] getStudyLevels() {return studyLevels;}
    public void setStudyLevels(String[] studyLevels) {this.studyLevels = studyLevels;}

    public String getWebsiteUrl() {return websiteUrl;}
    public void setWebsiteUrl(String websiteUrl) {this.websiteUrl = websiteUrl;}

    public String getBrochureUrl() {return brochureUrl;}
    public void setBrochureUrl(String brochureUrl) {this.brochureUrl = brochureUrl;}

    public String getLogoUrl() {return logoUrl;}
    public void setLogoUrl(String logoUrl) {this.logoUrl = logoUrl;}

    public BigDecimal getAverageRating() {return averageRating;}
    public void setAverageRating(BigDecimal averageRating) {this.averageRating = averageRating;}

    public Integer getReviewsCount() {return reviewsCount;}
    public void setReviewsCount(Integer reviewsCount) {this.reviewsCount = reviewsCount;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
