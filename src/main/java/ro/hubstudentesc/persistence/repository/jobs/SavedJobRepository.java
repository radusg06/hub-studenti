package ro.hubstudentesc.persistence.repository.jobs;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.jobs.SavedJob;
import ro.hubstudentesc.persistence.entity.jobs.SavedJobId;

import java.util.UUID;

public interface SavedJobRepository extends JpaRepository<SavedJob, SavedJobId> {
    boolean existsByUser_UserIdAndJob_Id(UUID userId, UUID jobId);
    void deleteByUser_UserIdAndJob_Id(UUID userId, UUID jobId);
}
