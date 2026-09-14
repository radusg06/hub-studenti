package ro.hubstudentesc.web.i18n;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.hubstudentesc.dto.i18n.BulkUpsertRequestDto;
import ro.hubstudentesc.dto.i18n.BulkUpsertResponseDto;
import ro.hubstudentesc.dto.i18n.LanguageListResponseDto;
import ro.hubstudentesc.dto.i18n.TranslationListResponseDto;
import ro.hubstudentesc.service.i18n.TranslationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TranslationController {

    private final TranslationService translationService;

    @GetMapping("/languages")
    public ResponseEntity<LanguageListResponseDto> getLanguages() {
        return ResponseEntity.ok(translationService.getLanguages());
    }

    @GetMapping("/translations")
    public ResponseEntity<TranslationListResponseDto> getTranslations(
            @RequestParam(required = false) String languageCode
    ) {
        return ResponseEntity.ok(
                translationService.getTranslations(languageCode)
        );
    }

    @PutMapping("/translations")
    public ResponseEntity<BulkUpsertResponseDto> bulkUpsert(
            @RequestBody @Valid BulkUpsertRequestDto request
    ) {
        return ResponseEntity.ok(
                translationService.bulkUpsert(request)
        );
    }

    @DeleteMapping("/translations/{languageCode}")
    public ResponseEntity<Void> deleteLanguage(
            @PathVariable String languageCode
    ) {
        translationService.deleteLanguage(languageCode);

        return ResponseEntity.noContent().build();
    }
}
