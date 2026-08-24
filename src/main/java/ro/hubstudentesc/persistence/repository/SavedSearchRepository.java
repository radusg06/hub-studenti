package ro.hubstudentesc.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.hubstudentesc.persistence.entity.SavedSearch;

import java.util.List;
import java.util.UUID;

@Repository
public interface SavedSearchRepository extends JpaRepository<SavedSearch , Long> {
    List<SavedSearch> findByUserId(UUID userId);
}
