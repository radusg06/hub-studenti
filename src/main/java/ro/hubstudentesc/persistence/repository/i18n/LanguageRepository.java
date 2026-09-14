package ro.hubstudentesc.persistence.repository.i18n;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.i18n.Language;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, String> {
    Optional<Language> findByCode(String code);

    Optional<Language> findByDefaultLanguageTrue();
}
