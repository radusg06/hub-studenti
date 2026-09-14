package ro.hubstudentesc.service.faculties;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.dto.faculties.FacultyDetailsResponseDto;
import ro.hubstudentesc.dto.faculties.FacultyResponseDto;
import ro.hubstudentesc.dto.faculties.ProgramResponseDto;
import ro.hubstudentesc.dto.faculties.ReviewCreateDto;
import ro.hubstudentesc.enums.facultiesEnums.DegreeLevel;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.faculties.FacultyNode;
import ro.hubstudentesc.persistence.entity.faculties.Program;
import ro.hubstudentesc.persistence.entity.faculties.Review;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;
import ro.hubstudentesc.persistence.repository.faculties.FacultyNodeRepository;
import ro.hubstudentesc.persistence.repository.faculties.ProgramRepository;
import ro.hubstudentesc.persistence.repository.faculties.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyNodeRepository facultyNodeRepository;
    private final ProgramRepository programRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public List<FacultyResponseDto> getFaculties(
            String city,
            String degree,
            String search
    ) {
        return facultyNodeRepository.findAll()
                .stream()
                .filter(faculty -> city == null ||
                        faculty.getCity().equalsIgnoreCase(city))
                .filter(faculty -> search == null ||
                        faculty.getName().toLowerCase().contains(search.toLowerCase()) ||
                        faculty.getUniversityName().toLowerCase().contains(search.toLowerCase()))
                .filter(faculty -> degree == null ||
                        programRepository.findByFaculty_Id(faculty.getId())
                                .stream()
                                .anyMatch(program ->
                                        program.getDegreeType() ==
                                                DegreeLevel.valueOf(degree)))
                .map(this::toFacultyResponseDto)
                .toList();
    }

    public FacultyDetailsResponseDto getFacultyBySlug(String slug) {
        FacultyNode faculty = facultyNodeRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Faculty not found"
                ));

        List<ProgramResponseDto> programs = programRepository
                .findByFaculty_Id(faculty.getId())
                .stream()
                .map(this::toProgramResponseDto)
                .toList();

        return new FacultyDetailsResponseDto(
                faculty.getId(),
                faculty.getName(),
                faculty.getUniversityName(),
                faculty.getDescription(),
                faculty.getAdmissionInfo(),
                faculty.getTuitionFeeAnnual(),
                programs,
                faculty.getAverageRating(),
                faculty.getReviewsCount()
        );
    }

    public UUID addReview(
            UUID facultyId,
            UUID userId,
            ReviewCreateDto dto
    ) {
        FacultyNode faculty = facultyNodeRepository.findById(facultyId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Faculty not found"
                ));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "User not found"
                ));

        if (reviewRepository.existsByFaculty_IdAndAuthor_Id(
                facultyId,
                userId
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "HUB_FAC_01"
            );
        }

        Review review = new Review();

        review.setFaculty(faculty);
        review.setAuthor(user);
        review.setRatingGeneral(dto.ratingGeneral());
        review.setRatingTeachers(dto.ratingTeachers());
        review.setRatingFacilities(dto.ratingFacilities());
        review.setRatingOpportunities(dto.ratingOpportunities());
        review.setComment(dto.comment());
        review.setVerifiedStudent(false);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review).getId();
    }

    private FacultyResponseDto toFacultyResponseDto(FacultyNode faculty) {
        return new FacultyResponseDto(
                faculty.getId(),
                faculty.getUniversityName(),
                faculty.getName(),
                faculty.getSlug(),
                faculty.getCity(),
                faculty.getTuitionFeeAnnual(),
                faculty.getAverageRating(),
                faculty.getReviewsCount(),
                faculty.getBrochureUrl()
        );
    }

    private ProgramResponseDto toProgramResponseDto(Program program) {
        return new ProgramResponseDto(
                program.getId(),
                program.getName(),
                program.getDegreeType(),
                program.getDurationYears(),
                program.getPlacesBudget(),
                program.getPlacesTax()
        );
    }
}
