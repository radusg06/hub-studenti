package ro.hubstudentesc.persistence.entity;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import ro.hubstudentesc.enums.JobApplicationStatus;
import ro.hubstudentesc.persistence.entity.auth.User;

@Entity
@NoArgsConstructor
@Table(name = "job_applications")
public class JobApplication {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id" , nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Column(length = 500)
    private String cv;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobApplicationStatus status;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id ;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user=user;
    }

    public String getCv()
    {
        return cv;
    }

    public void setCv(String cv)
    {
        this.cv=cv;
    }

    public JobApplicationStatus getStatus()
    {
        return status;
    }

    public void setStatus(JobApplicationStatus status)
    {
        this.status=status;
    }

    public Job getJob()
    {
        return job;
    }

    public void setJob(Job job)
    {
        this.job=job;
    }
}
