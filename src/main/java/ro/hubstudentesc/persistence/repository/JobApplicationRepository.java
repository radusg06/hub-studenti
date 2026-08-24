package ro.hubstudentesc.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.hubstudentesc.persistence.entity.JobApplication;

import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication , Long> {
    @Query("""
    SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
    FROM JobApplication a
    WHERE a.job.id = :jobId
      AND a.user.id = :userId
""")
    boolean existsByJobIdAndUserId(
            @Param("jobId") Long jobId,
            @Param("userId") UUID userId
    );
}
