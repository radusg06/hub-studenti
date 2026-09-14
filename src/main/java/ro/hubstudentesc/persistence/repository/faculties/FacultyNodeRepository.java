package ro.hubstudentesc.persistence.repository.faculties;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.faculties.FacultyNode;

import java.util.Optional;
import java.util.UUID;

public interface FacultyNodeRepository extends JpaRepository<FacultyNode, UUID> {

    Optional<FacultyNode> findBySlug(String slug);
}