package ro.hubstudentesc.persistence.repository.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.profiles.StudentProfile;

import java.util.UUID;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, UUID> {
}
