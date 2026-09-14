package ro.hubstudentesc.dto.billing;

import java.math.BigDecimal;

public record CheckoutSessionResponseDto(
        String checkoutUrl,
        String sessionId,
        BigDecimal amount,
        String currency
) {
}
