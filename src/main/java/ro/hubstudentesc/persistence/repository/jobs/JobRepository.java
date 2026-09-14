package ro.hubstudentesc.persistence.repository.jobs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.hubstudentesc.enums.jobEnums.JobType;
import ro.hubstudentesc.persistence.entity.jobs.Job;

import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {

    Page<Job> findByLocation(
            String location,
            Pageable pageable
    );

    Page<Job> findByType(
            JobType type,
            Pageable pageable
    );

    @Query(
            value = """
                    SELECT *
                    FROM jobs.job_postings
                    WHERE (
                        CAST(:location AS text) IS NULL
                        OR location = :location
                    )
                    AND (
                        CAST(:type AS text) IS NULL
                        OR type = :type
                    )
                    AND (
                        CAST(:skills AS text[]) IS NULL
                        OR required_skills && CAST(:skills AS text[])
                    )
                    ORDER BY created_at DESC
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM jobs.job_postings
                    WHERE (
                        CAST(:location AS text) IS NULL
                        OR location = :location
                    )
                    AND (
                        CAST(:type AS text) IS NULL
                        OR type = :type
                    )
                    AND (
                        CAST(:skills AS text[]) IS NULL
                        OR required_skills && CAST(:skills AS text[])
                    )
                    """,
            nativeQuery = true
    )
    Page<Job> findJobs(
            @Param("location") String location,
            @Param("type") String type,
            @Param("skills") String[] skills,
            Pageable pageable
    );
}