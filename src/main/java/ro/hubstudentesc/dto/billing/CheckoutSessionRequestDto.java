package ro.hubstudentesc.dto.billing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ro.hubstudentesc.enums.billing.PaymentPurpose;

import java.util.UUID;

public record CheckoutSessionRequestDto(
        @NotNull
        PaymentPurpose purpose,

        UUID listingId,

        @NotBlank
        String successUrl,

        @NotBlank
        String cancelUrl
) {
}