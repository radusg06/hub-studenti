package ro.hubstudentesc.persistence.entity.profiles;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "student_profiles", schema = "profiles")
public class StudentProfile {

    @Id
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private String faculty;

    private String specialization;

    @Column(nullable = false)
    private String studyCycle = "LICENTA";

    private Short studyYear;

    private String bio;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(nullable = false)
    private String[] skills = new String[0];

    private String cvUrl;
    private String githubUrl;
    private String linkedinUrl;
    private String portfolioUrl;

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

    public String getUniversity() {return university;}
    public void setUniversity(String university) {this.university = university;}

    public String getFaculty() {return faculty;}
    public void setFaculty(String faculty) {this.faculty = faculty;}

    public String getSpecialization() {return specialization;}
    public void setSpecialization(String specialization) {this.specialization = specialization;}

    public String getStudyCycle() {return studyCycle;}
    public void setStudyCycle(String studyCycle) {this.studyCycle = studyCycle;}

    public Short getStudyYear() {return studyYear;}
    public void setStudyYear(Short studyYear) {this.studyYear = studyYear;}

    public String getBio() {return bio;}
    public void setBio(String bio) {this.bio = bio;}

    public String[] getSkills() {return skills;}
    public void setSkills(String[] skills) {this.skills = skills;}

    public String getCvUrl() {return cvUrl;}
    public void setCvUrl(String cvUrl) {this.cvUrl = cvUrl;}

    public String getGithubUrl() {return githubUrl;}
    public void setGithubUrl(String githubUrl) {this.githubUrl = githubUrl;}

    public String getLinkedinUrl() {return linkedinUrl;}
    public void setLinkedinUrl(String linkedinUrl) {this.linkedinUrl = linkedinUrl;}

    public String getPortfolioUrl() {return portfolioUrl;}
    public void setPortfolioUrl(String portfolioUrl) {this.portfolioUrl = portfolioUrl;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
