package ro.hubstudentesc.dto.i18n;

public record TranslationDto(
        String translationId,
        String languageId,
        String translationKey,
        String text
) {
}
