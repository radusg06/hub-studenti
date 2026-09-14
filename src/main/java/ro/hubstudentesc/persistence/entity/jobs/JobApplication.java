package ro.hubstudentesc.persistence.entity.jobs;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import ro.hubstudentesc.enums.jobEnums.JobApplicationStatus;
import ro.hubstudentesc.persistence.entity.profiles.StudentProfile;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "job_applications" , schema = "jobs")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "job_id" , nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "student_id" , nullable = false)
    private StudentProfile student;

    @Column(name = "cv_url" , nullable = false)
    private String cvUrl;

    @Column(name = "cover_letter")
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobApplicationStatus status;

    @Column(name = "recruiter_notes")
    private String recruiterNotes;

    @Column(nullable = false)
    private LocalDateTime appliedAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public StudentProfile getStudent() { return student; }
    public void setStudent(StudentProfile student) { this.student = student; }

    public String getCvUrl() { return cvUrl; }
    public void setCvUrl(String cvUrl) { this.cvUrl = cvUrl; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }

    public JobApplicationStatus getStatus() { return status; }
    public void setStatus(JobApplicationStatus status) { this.status = status; }

    public String getRecruiterNotes() { return recruiterNotes; }
    public void setRecruiterNotes(String recruiterNotes) { this.recruiterNotes = recruiterNotes; }

    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
