package ro.hubstudentesc.persistence.repository.i18n;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.i18n.Translation;

import java.util.List;
import java.util.Optional;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    List<Translation> findByLanguage_LanguageIdOrderByTranslationKey(String languageId);

    Optional<Translation> findByLanguage_LanguageIdAndTranslationKey(String languageId, String translationKey);
}
