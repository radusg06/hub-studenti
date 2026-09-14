package ro.hubstudentesc.dto.i18n;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record LanguageDto(
        String languageId,
        String name,
        String code,
        @JsonProperty("default")
        boolean defaultLanguage,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {
}
