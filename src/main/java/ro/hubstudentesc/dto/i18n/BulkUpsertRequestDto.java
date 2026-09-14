package ro.hubstudentesc.dto.i18n;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BulkUpsertRequestDto(
        @Valid
        @NotNull
        LanguageUpsertDto language,

        @Valid
        @NotEmpty
        List<TranslationUpsertDto> translations
) {
}
