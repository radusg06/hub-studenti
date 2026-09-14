package ro.hubstudentesc.persistence.repository.marketplace;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.marketplace.ListingImage;

import java.util.List;
import java.util.UUID;
public interface ListingImageRepository extends JpaRepository<ListingImage , UUID> {
    List<ListingImage> findByListing_IdOrderByDisplayOrder(UUID id);
}
