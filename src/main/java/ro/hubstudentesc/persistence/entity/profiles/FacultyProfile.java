package ro.hubstudentesc.persistence.entity.profiles;

import jakarta.persistence.*;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "faculty_profiles", schema = "profiles")
public class FacultyProfile {

    @Id
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String universityName;

    @Column(nullable = false)
    private String facultyName;

    private String department;
    private String jobTitle;
    private String officeLocation;

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

    public String getUniversityName() {return universityName;}
    public void setUniversityName(String universityName) {this.universityName = universityName;}

    public String getFacultyName() {return facultyName;}
    public void setFacultyName(String facultyName) {this.facultyName = facultyName;}

    public String getDepartment() {return department;}
    public void setDepartment(String department) {this.department = department;}

    public String getJobTitle() {return jobTitle;}
    public void setJobTitle(String jobTitle) {this.jobTitle = jobTitle;}

    public String getOfficeLocation() {return officeLocation;}
    public void setOfficeLocation(String officeLocation) {this.officeLocation = officeLocation;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
