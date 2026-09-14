package ro.hubstudentesc.service.i18n;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.dto.i18n.BulkUpsertRequestDto;
import ro.hubstudentesc.dto.i18n.BulkUpsertResponseDto;
import ro.hubstudentesc.dto.i18n.BulkUpsertResultDto;
import ro.hubstudentesc.dto.i18n.LanguageDto;
import ro.hubstudentesc.dto.i18n.LanguageListResponseDto;
import ro.hubstudentesc.dto.i18n.TranslationDto;
import ro.hubstudentesc.dto.i18n.TranslationListResponseDto;
import ro.hubstudentesc.dto.i18n.TranslationUpsertDto;
import ro.hubstudentesc.persistence.entity.i18n.Language;
import ro.hubstudentesc.persistence.entity.i18n.Translation;
import ro.hubstudentesc.persistence.repository.i18n.LanguageRepository;
import ro.hubstudentesc.persistence.repository.i18n.TranslationRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private final LanguageRepository languageRepository;
    private final TranslationRepository translationRepository;

    @Transactional(readOnly = true)
    public LanguageListResponseDto getLanguages() {
        return new LanguageListResponseDto(
                languageRepository.findAll()
                        .stream()
                        .sorted(Comparator.comparing(Language::getCode))
                        .map(this::toLanguageDto)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public TranslationListResponseDto getTranslations(String languageCode) {
        if (languageCode == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "UUMDSIN_06");
        }

        if (languageCode.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UUMDSIN_05");
        }

        Language language = getLanguage(languageCode);

        return new TranslationListResponseDto(
                toLanguageDto(language),
                translationRepository.findByLanguage_LanguageIdOrderByTranslationKey(language.getLanguageId())
                        .stream()
                        .map(this::toTranslationDto)
                        .toList()
        );
    }

    @Transactional
    public BulkUpsertResponseDto bulkUpsert(BulkUpsertRequestDto request) {
        try {
            Language language = languageRepository.findByCode(request.language().code())
                    .orElseGet(() -> createLanguage(
                            request.language().name(),
                            request.language().code()
                    ));

            language.setName(request.language().name());
            language.setModifiedDate(LocalDateTime.now());
            languageRepository.save(language);

            validateDefaultKeys(language, request.translations());

            List<BulkUpsertResultDto> results = request.translations()
                    .stream()
                    .map(item -> upsertTranslation(language, item))
                    .toList();

            return new BulkUpsertResponseDto(
                    Long.parseLong(language.getLanguageId()),
                    language.getCode(),
                    results
            );
        } catch (DataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "UUMDSIN_08", exception);
        }
    }

    @Transactional
    public void deleteLanguage(String languageCode) {
        Language language = getLanguage(languageCode);

        if (language.isDefaultLanguage()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UUMDSIN_04");
        }

        languageRepository.delete(language);
    }

    private BulkUpsertResultDto upsertTranslation(
            Language language,
            TranslationUpsertDto item
    ) {
        Translation translation = findTranslation(language, item)
                .orElseGet(Translation::new);

        LocalDateTime now = LocalDateTime.now();

        translation.setLanguage(language);
        translation.setTranslationKey(item.translationKey());
        translation.setText(item.text());

        if (translation.getCreatedDate() == null) {
            translation.setCreatedDate(now);
        }

        translation.setModifiedDate(now);

        Translation saved = translationRepository.save(translation);

        return new BulkUpsertResultDto(
                saved.getTranslationId(),
                saved.getTranslationKey()
        );
    }

    private java.util.Optional<Translation> findTranslation(
            Language language,
            TranslationUpsertDto item
    ) {
        if (item.translationId() != null && !item.translationId().isBlank()) {
            return translationRepository.findById(Long.parseLong(item.translationId()));
        }

        return translationRepository.findByLanguage_LanguageIdAndTranslationKey(
                language.getLanguageId(),
                item.translationKey()
        );
    }

    private void validateDefaultKeys(
            Language language,
            List<TranslationUpsertDto> translations
    ) {
        Language defaultLanguage = languageRepository.findByDefaultLanguageTrue()
                .orElse(null);

        if (defaultLanguage == null
                || defaultLanguage.getLanguageId().equals(language.getLanguageId())) {
            return;
        }

        Set<String> defaultKeys = translationRepository
                .findByLanguage_LanguageIdOrderByTranslationKey(defaultLanguage.getLanguageId())
                .stream()
                .map(Translation::getTranslationKey)
                .collect(Collectors.toSet());

        Set<String> requestKeys = translations.stream()
                .map(TranslationUpsertDto::translationKey)
                .collect(Collectors.toSet());

        if (!defaultKeys.equals(requestKeys)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UUMDSIN_07");
        }
    }

    private Language getLanguage(String languageCode) {
        return languageRepository.findByCode(languageCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "UUMDSIN_03"
                ));
    }

    private Language createLanguage(String name, String code) {
        LocalDateTime now = LocalDateTime.now();

        long nextId = languageRepository.findAll()
                .stream()
                .map(Language::getLanguageId)
                .mapToLong(this::parseLanguageId)
                .max()
                .orElse(0L) + 1L;

        Language language = new Language();
        language.setLanguageId(Long.toString(nextId));
        language.setName(name);
        language.setCode(code);
        language.setDefaultLanguage(false);
        language.setCreatedDate(now);
        language.setModifiedDate(now);

        return language;
    }

    private long parseLanguageId(String languageId) {
        try {
            return Long.parseLong(languageId);
        } catch (NumberFormatException exception) {
            return 0L;
        }
    }

    private LanguageDto toLanguageDto(Language language) {
        return new LanguageDto(
                language.getLanguageId(),
                language.getName(),
                language.getCode(),
                language.isDefaultLanguage(),
                language.getCreatedDate(),
                language.getModifiedDate()
        );
    }

    private TranslationDto toTranslationDto(Translation translation) {
        return new TranslationDto(
                translation.getTranslationId().toString(),
                translation.getLanguage().getLanguageId(),
                translation.getTranslationKey(),
                translation.getText()
        );
    }
}
