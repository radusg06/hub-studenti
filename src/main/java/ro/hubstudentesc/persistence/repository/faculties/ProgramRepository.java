package ro.hubstudentesc.persistence.repository.faculties;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.faculties.Program;

import java.util.List;
import java.util.UUID;

public interface ProgramRepository extends JpaRepository<Program, UUID> {

    List<Program> findByFaculty_Id(UUID facultyId);
}