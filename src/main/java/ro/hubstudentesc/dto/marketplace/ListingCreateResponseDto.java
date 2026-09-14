package ro.hubstudentesc.dto.marketplace;

import java.util.UUID;

public record ListingCreateResponseDto(
        boolean success,
        String message,
        UUID listingId
) {
}
