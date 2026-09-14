package ro.hubstudentesc.dto.i18n;

import java.util.List;

public record TranslationListResponseDto(
        LanguageDto language,
        List<TranslationDto> translations
) {
}
