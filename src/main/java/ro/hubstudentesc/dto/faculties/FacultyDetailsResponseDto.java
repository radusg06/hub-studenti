package ro.hubstudentesc.dto.faculties;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record FacultyDetailsResponseDto(
        UUID id,
        String name,
        String university,
        String description,
        String admissionInfo,
        BigDecimal tuitionFeeAnnual,
        List<ProgramResponseDto> programs,
        BigDecimal averageRating,
        Integer reviewsCount
) {
}