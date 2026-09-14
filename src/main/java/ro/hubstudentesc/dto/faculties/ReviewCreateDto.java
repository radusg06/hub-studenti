package ro.hubstudentesc.dto.faculties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewCreateDto(
        @NotNull
        @Min(1)
        @Max(5)
        Short ratingGeneral,

        @Min(1)
        @Max(5)
        Short ratingTeachers,

        @Min(1)
        @Max(5)
        Short ratingFacilities,

        @Min(1)
        @Max(5)
        Short ratingOpportunities,

        @NotBlank
        String comment
) {
}