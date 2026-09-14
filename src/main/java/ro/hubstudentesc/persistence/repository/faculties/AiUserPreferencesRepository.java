package ro.hubstudentesc.persistence.repository.faculties;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.faculties.AiUserPreferences;

import java.util.UUID;

public interface AiUserPreferencesRepository extends JpaRepository<AiUserPreferences, UUID> {
}