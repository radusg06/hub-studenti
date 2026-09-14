package ro.hubstudentesc.dto.billing;

import ro.hubstudentesc.enums.billing.SubscriptionTier;

import java.time.LocalDateTime;
import java.util.List;

public record SubscriptionResponseDto(
        SubscriptionTier tier,
        boolean isActive,
        LocalDateTime currentPeriodEnd,
        List<String> benefits
) {
}