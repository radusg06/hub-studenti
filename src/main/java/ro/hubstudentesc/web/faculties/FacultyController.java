package ro.hubstudentesc.web.faculties;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.common.DataEnvelopeDto;
import ro.hubstudentesc.dto.faculties.AIRankingRequestDto;
import ro.hubstudentesc.dto.faculties.AIRankingResponseDto;
import ro.hubstudentesc.dto.faculties.FacultyDetailsResponseDto;
import ro.hubstudentesc.dto.faculties.FacultyResponseDto;
import ro.hubstudentesc.dto.faculties.ReviewCreateDto;
import ro.hubstudentesc.dto.faculties.ReviewCreateResponseDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.service.faculties.AIRankingService;
import ro.hubstudentesc.service.faculties.FacultyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/faculties")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;
    private final AIRankingService aiRankingService;

    @GetMapping
    public ResponseEntity<DataEnvelopeDto<List<FacultyResponseDto>>> getFaculties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String degree,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(new DataEnvelopeDto<>(
                facultyService.getFaculties(city, degree, search)
        ));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<FacultyDetailsResponseDto> getFacultyBySlug(
            @PathVariable String slug
    ) {
        return ResponseEntity.ok(
                facultyService.getFacultyBySlug(slug)
        );
    }

    @PostMapping("/{facultyId}/reviews")
    public ResponseEntity<ReviewCreateResponseDto> addReview(
            Authentication authentication,
            @PathVariable UUID facultyId,
            @RequestBody @Valid ReviewCreateDto dto
    ) {
        User user = (User) authentication.getPrincipal();
        UUID reviewId = facultyService.addReview(facultyId, user.getId(), dto);

        return ResponseEntity.status(201).body(new ReviewCreateResponseDto(
                true,
                "Recenzia a fost adaugata si media facultatii a fost actualizata.",
                reviewId
        ));
    }

    @PostMapping("/ai-ranking")
    public ResponseEntity<AIRankingResponseDto> generateRanking(
            @RequestBody @Valid AIRankingRequestDto dto
    ) {
        return ResponseEntity.ok(
                aiRankingService.generateRanking(dto)
        );
    }
}
