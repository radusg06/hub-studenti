package ro.hubstudentesc.service.faculties;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.faculties.AIRankingItemDto;
import ro.hubstudentesc.dto.faculties.AIRankingRequestDto;
import ro.hubstudentesc.dto.faculties.AIRankingResponseDto;
import ro.hubstudentesc.enums.facultiesEnums.DegreeLevel;
import ro.hubstudentesc.persistence.entity.faculties.FacultyNode;
import ro.hubstudentesc.persistence.entity.faculties.Program;
import ro.hubstudentesc.persistence.repository.faculties.FacultyNodeRepository;
import ro.hubstudentesc.persistence.repository.faculties.ProgramRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIRankingService {

    private final FacultyNodeRepository facultyNodeRepository;
    private final ProgramRepository programRepository;

    /*
     * Generează clasamentul facultăților pentru student.
     * Pentru fiecare facultate verifică programele disponibile și îl alege pe cel
     * mai potrivit în funcție de nivelul de studiu și interesele studentului.
     * Apoi calculează un scor pe baza bugetului, orașului, intereselor,
     * notei și ratingului facultății, sortează facultățile după scor
     * și returnează clasamentul împreună cu programul recomandat.
     */

    public AIRankingResponseDto generateRanking(AIRankingRequestDto dto) {

        List<AIRankingItemDto> ranking = new ArrayList<>();

        for (FacultyNode faculty : facultyNodeRepository.findAll()) {

            List<Program> programs = programRepository
                    .findByFaculty_Id(faculty.getId());

            Program bestProgram = programs.stream()
                    .filter(program ->
                            dto.preferredDegree() == null ||
                                    program.getDegreeType() == dto.preferredDegree()
                    )
                    .max(Comparator.comparingInt(
                            program -> calculateProgramScore(program, dto)
                    ))
                    .orElse(null);

            if (bestProgram == null) {
                continue;
            }

            int score = calculateScore(faculty, bestProgram, dto);

            ranking.add(
                    new AIRankingItemDto(
                            0,
                            score,
                            faculty.getName() + " - " + faculty.getUniversityName(),
                            faculty.getSlug(),
                            bestProgram.getName(),
                            generateRationale(faculty, bestProgram, dto, score)
                    )
            );
        }

        ranking.sort(
                Comparator.comparingInt(AIRankingItemDto::matchScore)
                        .reversed()
        );

        List<AIRankingItemDto> rankedFaculties = new ArrayList<>();

        for (int i = 0; i < ranking.size(); i++) {
            AIRankingItemDto item = ranking.get(i);

            rankedFaculties.add(
                    new AIRankingItemDto(
                            i + 1,
                            item.matchScore(),
                            item.facultyName(),
                            item.slug(),
                            item.recommendedProgram(),
                            item.aiRationale()
                    )
            );
        }

        return new AIRankingResponseDto(
                true,
                rankedFaculties
        );
    }

    private int calculateScore(
            FacultyNode faculty,
            Program program,
            AIRankingRequestDto dto
    ) {
        int score = 0;

        if (dto.budgetLimit() != null &&
                faculty.getTuitionFeeAnnual().compareTo(dto.budgetLimit()) <= 0) {
            score += 30;
        }

        if (dto.preferredCities() != null &&
                dto.preferredCities().stream()
                        .anyMatch(city -> city.equalsIgnoreCase(faculty.getCity()))) {
            score += 25;
        }

        score += calculateProgramScore(program, dto);

        if (dto.highschoolGrade() != null &&
                dto.highschoolGrade().doubleValue() >= 9.0) {
            score += 10;
        }

        if (faculty.getAverageRating() != null &&
                faculty.getAverageRating().doubleValue() >= 4.0) {
            score += 10;
        }

        return Math.min(score, 100);
    }

    private int calculateProgramScore(
            Program program,
            AIRankingRequestDto dto
    ) {
        if (dto.interests() == null || dto.interests().isEmpty()) {
            return 15;
        }

        int score = 15;

        String programName = program.getName().toLowerCase();

        for (String interest : dto.interests()) {
            if (programName.contains(interest.toLowerCase())) {
                score += 10;
            }
        }

        return Math.min(score, 35);
    }

    private String generateRationale(
            FacultyNode faculty,
            Program program,
            AIRankingRequestDto dto,
            int score
    ) {
        return "Potrivire de " + score + "% pe baza preferințelor introduse.";
    }
}