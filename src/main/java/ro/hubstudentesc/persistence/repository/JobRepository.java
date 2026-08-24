package ro.hubstudentesc.persistence.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.hubstudentesc.persistence.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job , Long> {
    @Query("""
    SELECT j FROM Job j
    WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
    OR LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%'))
    OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    Page<Job> searchByKeyword(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    Page<Job> findByCityIgnoreCase(
            String city,
            Pageable pageable
    );

    Page<Job> findByProgramIgnoreCase(
            String program,
            Pageable pageable
    );
}
