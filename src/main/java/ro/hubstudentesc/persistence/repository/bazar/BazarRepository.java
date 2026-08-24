package ro.hubstudentesc.persistence.repository.bazar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.hubstudentesc.persistence.entity.marketplace.MarketPlace;

@Repository
public interface BazarRepository extends JpaRepository<MarketPlace, Long> {
}
