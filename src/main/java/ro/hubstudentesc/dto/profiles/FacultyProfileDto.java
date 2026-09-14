package ro.hubstudentesc.dto.profiles;

import java.util.UUID;

public record FacultyProfileDto(
        UUID userId,
        String universityName,
        String facultyName,
        String department,
        String jobTitle,
        String officeLocation
) {
}
