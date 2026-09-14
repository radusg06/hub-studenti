package ro.hubstudentesc.web.billing;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.billing.CheckoutSessionRequestDto;
import ro.hubstudentesc.dto.billing.CheckoutSessionResponseDto;
import ro.hubstudentesc.dto.billing.SubscriptionResponseDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.service.billing.BillingService;

import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/checkout/session")
    public ResponseEntity<CheckoutSessionResponseDto> createCheckoutSession(
            Authentication authentication,
            @RequestBody @Valid CheckoutSessionRequestDto dto
    ) {
        User user = (User) authentication.getPrincipal();

        CheckoutSessionResponseDto response =
                billingService.createCheckoutSession(
                        user.getId(),
                        dto
                );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/webhooks/stripe")
    public ResponseEntity<Map<String, Boolean>> stripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature
    ) {
        billingService.handleWebhook(payload, signature);

        return ResponseEntity.ok(Map.of("received", true));
    }

    @GetMapping("/subscriptions/me")
    public ResponseEntity<SubscriptionResponseDto> getMySubscription(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        SubscriptionResponseDto response =
                billingService.getMySubscription(user.getId());

        return ResponseEntity.ok(response);
    }
}
