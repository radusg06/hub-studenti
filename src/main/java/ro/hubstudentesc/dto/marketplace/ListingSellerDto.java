package ro.hubstudentesc.dto.marketplace;

import java.util.UUID;

public record ListingSellerDto(
        UUID id,
        String fullName
) {
}
