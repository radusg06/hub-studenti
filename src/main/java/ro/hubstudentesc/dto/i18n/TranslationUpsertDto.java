package ro.hubstudentesc.dto.i18n;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TranslationUpsertDto(
        String translationId,

        @NotBlank
        @Size(max = 255)
        String translationKey,

        @NotBlank
        String text
) {
}
