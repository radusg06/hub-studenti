package ro.hubstudentesc.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.Accommodation;


public interface AccommodationRepository extends JpaRepository<Accommodation , Long> {
}
