package ro.hubstudentesc.persistence.repository.marketplace;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.hubstudentesc.enums.marketPlaceEnums.ListingStatus;
import ro.hubstudentesc.persistence.entity.marketplace.Listing;

import java.math.BigDecimal;
import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, UUID> {

    @Query("""
        SELECT l
        FROM Listing l
        WHERE (:categoryId IS NULL OR l.category.id = :categoryId)
          AND (:minPrice IS NULL OR l.price >= :minPrice)
          AND (:maxPrice IS NULL OR l.price <= :maxPrice)
          AND (
              :location IS NULL
              OR LOWER(l.location) LIKE CONCAT(
                  '%',
                  LOWER(CAST(:location AS string)),
                  '%'
              )
          )
          AND (:promoted IS NULL OR l.isPromoted = :promoted)
          AND l.status <> :archivedStatus
        ORDER BY l.isPromoted DESC, l.createdAt DESC
        """)
    Page<Listing> findListings(
            @Param("categoryId") Long categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("location") String location,
            @Param("promoted") Boolean promoted,
            @Param("archivedStatus") ListingStatus archivedStatus,
            Pageable pageable
    );
}