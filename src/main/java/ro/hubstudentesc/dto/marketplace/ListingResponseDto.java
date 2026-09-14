package ro.hubstudentesc.dto.marketplace;

import ro.hubstudentesc.enums.marketPlaceEnums.ListingCondition;
import ro.hubstudentesc.enums.marketPlaceEnums.ListingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ListingResponseDto(
        UUID id,

        String title,

        String description,

        String category,

        BigDecimal price,

        String currency,

        ListingCondition condition,

        ListingStatus status,

        String location,

        boolean isNegotiable,

        boolean isPromoted,

        boolean requiresPayment,

        ListingSellerDto seller,

        ListingContactDto contact,

        List<String> images,

        Integer viewsCount,

        LocalDateTime createdAt
) {
}
