package ro.hubstudentesc.dto.faculties;

import java.math.BigDecimal;
import java.util.UUID;

public record FacultyResponseDto(
        UUID id,
        String universityName,
        String name,
        String slug,
        String city,
        BigDecimal tuitionFeeAnnual,
        BigDecimal averageRating,
        Integer reviewsCount,
        String brochureUrl
) {
}