package ro.hubstudentesc.dto.i18n;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LanguageUpsertDto(
        @NotBlank
        @Size(max = 255)
        String name,

        @NotBlank
        @Size(max = 10)
        String code
) {
}
