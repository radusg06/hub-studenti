package ro.hubstudentesc.dto.profiles;

import ro.hubstudentesc.dto.auth.UserRecordDto;

public record CurrentProfileDto(
        UserRecordDto account,
        StudentProfileDto studentProfile,
        CompanyProfileDto companyProfile,
        FacultyProfileDto facultyProfile
) {
}
