package ro.hubstudentesc.persistence.entity.jobs;

import jakarta.persistence.*;
import ro.hubstudentesc.persistence.entity.profiles.StudentProfile;

import java.time.LocalDateTime;

@Entity
@Table(name = "saved_jobs" , schema = "jobs")
@IdClass(SavedJobId.class)
public class SavedJob {
    @ManyToOne
    @Id
    @JoinColumn(name = "user_id" , nullable = false)
    private StudentProfile user;

    @ManyToOne
    @Id
    @JoinColumn(name = "job_id" , nullable = false)
    private Job job;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public StudentProfile getUser() { return user; }
    public void setUser(StudentProfile user) { this.user = user; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
