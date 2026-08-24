package ro.hubstudentesc.dto;

import java.util.UUID;

public record SavedSearchRecordDto(
        Long id,
        UUID userId,
        String keyword,
        String city,
        String program
) {
}
