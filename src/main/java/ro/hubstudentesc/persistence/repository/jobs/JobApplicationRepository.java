package ro.hubstudentesc.persistence.repository.jobs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.hubstudentesc.persistence.entity.jobs.JobApplication;

import java.util.UUID;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {

    boolean existsByJob_IdAndStudent_UserId(UUID jobId, UUID studentId);
}