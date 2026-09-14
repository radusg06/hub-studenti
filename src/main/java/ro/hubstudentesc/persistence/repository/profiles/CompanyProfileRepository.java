package ro.hubstudentesc.persistence.repository.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.profiles.CompanyProfile;

import java.util.Optional;
import java.util.UUID;

public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, UUID> {
    boolean existsByCuiCifAndUserIdNot(String cuiCif, UUID userId);
    Optional<CompanyProfile> findByCuiCif(String cuiCif);
}
