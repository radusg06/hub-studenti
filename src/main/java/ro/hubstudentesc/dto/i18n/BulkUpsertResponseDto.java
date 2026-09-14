package ro.hubstudentesc.dto.i18n;

import java.util.List;

public record BulkUpsertResponseDto(
        long languageId,
        String languageCode,
        List<BulkUpsertResultDto> results
) {
}
