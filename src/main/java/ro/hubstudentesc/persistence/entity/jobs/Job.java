package ro.hubstudentesc.persistence.entity.jobs;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ro.hubstudentesc.enums.jobEnums.JobStatus;
import ro.hubstudentesc.enums.jobEnums.JobType;
import ro.hubstudentesc.enums.jobEnums.WorkplaceType;
import ro.hubstudentesc.persistence.entity.profiles.CompanyProfile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "job_postings" , schema = "jobs")
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "company_id" , nullable = false)
    private CompanyProfile company;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false)
    @NotBlank
    private String description;

    private String requirements;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkplaceType workplace;

    @Column(nullable = false)
    @NotBlank
    private String location;

    @Column(nullable = false)
    private String experienceLevel;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "required_skills" , nullable = false)
    private String[] requiredSkills;

    @Column(name = "salary_min")
    @PositiveOrZero
    private BigDecimal salaryMin;

    @Column(name = "salary_max")
    @PositiveOrZero
    private BigDecimal salaryMax;

    @Column(name = "salary_currency")
    private String salaryCurrency;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public CompanyProfile getCompany() { return company; }
    public void setCompany(CompanyProfile company) { this.company = company; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }

    public JobType getType() { return type; }
    public void setType(JobType type) { this.type = type; }

    public WorkplaceType getWorkplace() { return workplace; }
    public void setWorkplace(WorkplaceType workplace) { this.workplace = workplace; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public String[] getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(String[] requiredSkills) { this.requiredSkills = requiredSkills; }

    public BigDecimal getSalaryMin() { return salaryMin; }
    public void setSalaryMin(BigDecimal salaryMin) { this.salaryMin = salaryMin; }

    public BigDecimal getSalaryMax() { return salaryMax; }
    public void setSalaryMax(BigDecimal salaryMax) { this.salaryMax = salaryMax; }

    public String getSalaryCurrency() { return salaryCurrency; }
    public void setSalaryCurrency(String salaryCurrency) { this.salaryCurrency = salaryCurrency; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }

}
