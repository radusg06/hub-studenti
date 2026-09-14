package ro.hubstudentesc.dto.faculties;

public record AIRankingItemDto(
        Integer rank,
        Integer matchScore,
        String facultyName,
        String slug,
        String recommendedProgram,
        String aiRationale
) {
}