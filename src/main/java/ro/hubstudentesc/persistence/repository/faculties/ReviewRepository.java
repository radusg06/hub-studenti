package ro.hubstudentesc.persistence.repository.faculties;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.faculties.Review;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    boolean existsByFaculty_IdAndAuthor_Id(UUID facultyId, UUID authorId);
}