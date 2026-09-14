package ro.hubstudentesc.dto.faculties;

import java.util.UUID;

public record ReviewResponseDto(
        UUID id,
        Short ratingGeneral,
        Short ratingTeachers,
        Short ratingFacilities,
        Short ratingOpportunities,
        String comment
) {
}