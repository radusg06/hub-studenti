package ro.hubstudentesc.dto.i18n;

import java.util.List;

public record LanguageListResponseDto(
        List<LanguageDto> languages
) {
}
