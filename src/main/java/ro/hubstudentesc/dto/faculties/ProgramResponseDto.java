package ro.hubstudentesc.dto.faculties;

import ro.hubstudentesc.enums.facultiesEnums.DegreeLevel;

import java.util.UUID;

public record ProgramResponseDto(
        UUID id,
        String name,
        DegreeLevel degreeType,
        Short durationYears,
        Integer placesBudget,
        Integer placesTax
) {
}