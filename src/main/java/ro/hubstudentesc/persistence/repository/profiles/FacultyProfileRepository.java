package ro.hubstudentesc.persistence.repository.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.profiles.FacultyProfile;

import java.util.UUID;

public interface FacultyProfileRepository extends JpaRepository<FacultyProfile, UUID> {
}
