package ro.hubstudentesc.dto.faculties;

import java.util.List;

public record AIRankingResponseDto(
        boolean success,
        List<AIRankingItemDto> rankedFaculties
) {
}