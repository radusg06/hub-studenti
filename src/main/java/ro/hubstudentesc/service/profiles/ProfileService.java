package ro.hubstudentesc.service.profiles;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.dto.auth.UserRecordDto;
import ro.hubstudentesc.dto.profiles.CompanyProfileDto;
import ro.hubstudentesc.dto.profiles.CurrentProfileDto;
import ro.hubstudentesc.dto.profiles.FacultyProfileDto;
import ro.hubstudentesc.dto.profiles.StudentProfileDto;
import ro.hubstudentesc.enums.authEnums.UserRole;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.profiles.CompanyProfile;
import ro.hubstudentesc.persistence.entity.profiles.FacultyProfile;
import ro.hubstudentesc.persistence.entity.profiles.StudentProfile;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;
import ro.hubstudentesc.persistence.repository.profiles.CompanyProfileRepository;
import ro.hubstudentesc.persistence.repository.profiles.FacultyProfileRepository;
import ro.hubstudentesc.persistence.repository.profiles.StudentProfileRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final FacultyProfileRepository facultyProfileRepository;

    public CurrentProfileDto getCurrentProfile(UUID userId) {
        User user = getUser(userId);

        return new CurrentProfileDto(
                toUserDto(user),
                studentProfileRepository.findById(userId).map(this::toStudentDto).orElse(null),
                companyProfileRepository.findById(userId).map(this::toCompanyDto).orElse(null),
                facultyProfileRepository.findById(userId).map(this::toFacultyDto).orElse(null)
        );
    }

    public StudentProfileDto upsertStudentProfile(UUID userId, StudentProfileDto dto) {
        User user = getUser(userId);
        requireRole(user, UserRole.STUDENT);

        StudentProfile profile = studentProfileRepository.findById(userId).orElseGet(StudentProfile::new);
        profile.setUser(user);
        profile.setUniversity(dto.university());
        profile.setFaculty(dto.faculty());
        profile.setSpecialization(dto.specialization());
        profile.setStudyCycle(dto.studyCycle() == null ? "LICENTA" : dto.studyCycle());
        profile.setStudyYear(dto.studyYear());
        profile.setBio(dto.bio());
        profile.setSkills(dto.skills() == null ? new String[0] : dto.skills());
        profile.setCvUrl(dto.cvUrl());
        profile.setGithubUrl(dto.githubUrl());
        profile.setLinkedinUrl(dto.linkedinUrl());
        profile.setPortfolioUrl(dto.portfolioUrl());

        return toStudentDto(studentProfileRepository.save(profile));
    }

    public CompanyProfileDto upsertCompanyProfile(UUID userId, CompanyProfileDto dto) {
        User user = getUser(userId);
        requireRole(user, UserRole.EMPLOYER);

        if (dto.cuiCif() != null && companyProfileRepository.existsByCuiCifAndUserIdNot(dto.cuiCif(), userId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CUI/CIF duplicat");
        }

        CompanyProfile profile = companyProfileRepository.findById(userId).orElseGet(CompanyProfile::new);
        profile.setUser(user);
        profile.setCompanyName(dto.companyName());
        profile.setCuiCif(dto.cuiCif());
        profile.setIndustry(dto.industry());
        profile.setDescription(dto.description());
        profile.setWebsiteUrl(dto.websiteUrl());
        profile.setLogoUrl(dto.logoUrl());
        profile.setCity(dto.city() == null ? "Bucuresti" : dto.city());
        profile.setAddress(dto.address());
        profile.setContactPhone(dto.contactPhone());

        return toCompanyDto(companyProfileRepository.save(profile));
    }

    public StudentProfileDto getPublicStudentProfile(UUID studentId) {
        return studentProfileRepository.findById(studentId)
                .map(this::toStudentDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profil student inexistent"));
    }

    private User getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilizator inexistent"));
    }

    private void requireRole(User user, UserRole role) {
        if (user.getRole() != role) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Rol insuficient");
        }
    }

    private UserRecordDto toUserDto(User user) {
        return new UserRecordDto(
                user.getId(),
                user.getGivenName(),
                user.getFamilyName(),
                user.getSub(),
                user.getEmail(),
                user.getPicture(),
                user.getRole(),
                user.getIsVerified(),
                user.getIsActive()
        );
    }

    private StudentProfileDto toStudentDto(StudentProfile profile) {
        return new StudentProfileDto(
                profile.getUserId(),
                profile.getUniversity(),
                profile.getFaculty(),
                profile.getSpecialization(),
                profile.getStudyCycle(),
                profile.getStudyYear(),
                profile.getBio(),
                profile.getSkills(),
                profile.getCvUrl(),
                profile.getGithubUrl(),
                profile.getLinkedinUrl(),
                profile.getPortfolioUrl()
        );
    }

    private CompanyProfileDto toCompanyDto(CompanyProfile profile) {
        return new CompanyProfileDto(
                profile.getUserId(),
                profile.getCompanyName(),
                profile.getCuiCif(),
                profile.getIndustry(),
                profile.getDescription(),
                profile.getWebsiteUrl(),
                profile.getLogoUrl(),
                profile.getCity(),
                profile.getAddress(),
                profile.getContactPhone()
        );
    }

    private FacultyProfileDto toFacultyDto(FacultyProfile profile) {
        return new FacultyProfileDto(
                profile.getUserId(),
                profile.getUniversityName(),
                profile.getFacultyName(),
                profile.getDepartment(),
                profile.getJobTitle(),
                profile.getOfficeLocation()
        );
    }
}
