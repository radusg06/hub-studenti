package ro.hubstudentesc.service.billing;

import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.dto.billing.CheckoutSessionRequestDto;
import ro.hubstudentesc.dto.billing.CheckoutSessionResponseDto;
import ro.hubstudentesc.dto.billing.SubscriptionResponseDto;
import ro.hubstudentesc.enums.authEnums.UserRole;
import ro.hubstudentesc.enums.billing.PaymentPurpose;
import ro.hubstudentesc.enums.billing.PaymentStatus;
import ro.hubstudentesc.enums.billing.SubscriptionTier;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.billing.Subscription;
import ro.hubstudentesc.persistence.entity.billing.Transaction;
import ro.hubstudentesc.persistence.entity.marketplace.Listing;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;
import ro.hubstudentesc.persistence.repository.billing.SubscriptionRepository;
import ro.hubstudentesc.persistence.repository.billing.TransactionRepository;
import ro.hubstudentesc.persistence.repository.marketplace.ListingRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final TransactionRepository transactionRepository;
    private final ListingRepository listingRepository;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.webhook-secret}")
    private String stripeWebhookSecret;

    public CheckoutSessionResponseDto createCheckoutSession(
            UUID userId,
            CheckoutSessionRequestDto dto
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilizatorul nu exista"
                ));

        validateUserRole(user, dto.purpose());

        Listing listing = null;

        if (dto.listingId() != null) {
            listing = listingRepository.findById(dto.listingId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Anuntul nu exista"
                    ));
        }

        validateListingForPayment(user, listing, dto.purpose());

        BigDecimal amount = getAmount(dto.purpose(), listing);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setListing(listing);
        transaction.setAmount(amount);
        transaction.setCurrency("RON");
        transaction.setStatus(PaymentStatus.PENDING);
        transaction.setPurpose(dto.purpose());

        LocalDateTime now = LocalDateTime.now();

        transaction.setCreatedAt(now);
        transaction.setUpdatedAt(now);

        transactionRepository.save(transaction);

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams.LineItem.PriceData.Builder priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("ron")
                        .setUnitAmount(
                                amount
                                        .multiply(BigDecimal.valueOf(100))
                                        .longValue()
                        )
                        .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData
                                        .builder()
                                        .setName(getProductName(dto.purpose()))
                                        .build()
                        );

        SessionCreateParams.Mode mode;

        if (dto.purpose() == PaymentPurpose.PREMIUM_SUB) {

            priceData.setRecurring(
                    SessionCreateParams.LineItem.PriceData.Recurring
                            .builder()
                            .setInterval(
                                    SessionCreateParams.LineItem.PriceData
                                            .Recurring.Interval.MONTH
                            )
                            .build()
            );

            mode = SessionCreateParams.Mode.SUBSCRIPTION;

        } else {
            mode = SessionCreateParams.Mode.PAYMENT;
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(mode)
                .setSuccessUrl(dto.successUrl())
                .setCancelUrl(dto.cancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(priceData.build())
                                .build()
                )
                .putMetadata("userId", userId.toString())
                .putMetadata("purpose", dto.purpose().name())
                .putMetadata(
                        "listingId",
                        listing != null
                                ? listing.getId().toString()
                                : ""
                )
                .putMetadata(
                        "transactionId",
                        transaction.getId().toString()
                )
                .build();

        try {

            Session session = Session.create(params);

            transaction.setPaymentGatewayRef(session.getId());
            transaction.setUpdatedAt(LocalDateTime.now());

            transactionRepository.save(transaction);

            return new CheckoutSessionResponseDto(
                    session.getUrl(),
                    session.getId(),
                    amount,
                    "RON"
            );

        } catch (Exception e) {

            transaction.setStatus(PaymentStatus.FAILED);
            transaction.setUpdatedAt(LocalDateTime.now());

            transactionRepository.save(transaction);

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Nu s-a putut crea sesiunea Stripe"
            );
        }
    }

    public void handleWebhook(
            String payload,
            String signature
    ) {
        try {

            Event event = Webhook.constructEvent(
                    payload,
                    signature,
                    stripeWebhookSecret
            );

            if ("checkout.session.completed".equals(event.getType())) {

                StripeObject object = event
                        .getDataObjectDeserializer()
                        .getObject()
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Nu s-a putut deserializa sesiunea Stripe"
                                )
                        );

                if (object instanceof Session session) {
                    completeTransaction(session);
                }
            }

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Webhook Stripe invalid"
            );
        }
    }

    private void completeTransaction(Session session) {

        Transaction transaction = transactionRepository
                .findByPaymentGatewayRef(session.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tranzactia Stripe nu exista"
                ));

        if (transaction.getStatus() == PaymentStatus.COMPLETED) {
            return;
        }

        transaction.setStatus(PaymentStatus.COMPLETED);
        transaction.setUpdatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);

        if (transaction.getPurpose() == PaymentPurpose.PREMIUM_SUB) {
            activatePremium(
                    transaction.getUser(),
                    session
            );
        }

        if (transaction.getPurpose() == PaymentPurpose.PROMOTED_LISTING) {
            promoteListing(transaction.getListing());
        }
    }

    private void activatePremium(
            User user,
            Session session
    ) {
        Subscription subscription = subscriptionRepository
                .findByUser_Id(user.getId())
                .orElseGet(Subscription::new);

        LocalDateTime now = LocalDateTime.now();

        subscription.setUser(user);
        subscription.setTier(SubscriptionTier.STUDENT_PREMIUM);
        subscription.setActive(true);

        if (session.getCustomer() != null) {
            subscription.setStripeCustomerId(
                    session.getCustomer()
            );
        }

        if (session.getSubscription() != null) {
            subscription.setStripeSubId(
                    session.getSubscription()
            );
        }

        if (subscription.getCreatedAt() == null) {
            subscription.setCreatedAt(now);
        }

        subscription.setCurrentPeriodEnd(
                now.plusMonths(1)
        );

        subscription.setUpdatedAt(now);

        subscriptionRepository.save(subscription);
    }

    private void promoteListing(Listing listing) {

        if (listing == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Anuntul este obligatoriu pentru promovare"
            );
        }

        listing.setPromoted(true);
        listing.setPromotedUntil(
                LocalDateTime.now().plusDays(30)
        );
        listing.setUpdatedAt(LocalDateTime.now());

        listingRepository.save(listing);
    }

    public SubscriptionResponseDto getMySubscription(
            UUID userId
    ) {
        Subscription subscription = subscriptionRepository
                .findByUser_Id(userId)
                .orElseGet(() ->
                        createFreeSubscription(userId)
                );

        boolean active =
                subscription.isActive()
                        && (
                        subscription.getCurrentPeriodEnd() == null
                                || subscription.getCurrentPeriodEnd()
                                .isAfter(LocalDateTime.now())
                );

        if (subscription.isActive() != active) {
            subscription.setActive(active);
            subscription.setUpdatedAt(LocalDateTime.now());
            subscriptionRepository.save(subscription);
        }

        return new SubscriptionResponseDto(
                subscription.getTier(),
                active,
                subscription.getCurrentPeriodEnd(),
                getBenefits(subscription.getTier())
        );
    }

    private Subscription createFreeSubscription(
            UUID userId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilizatorul nu exista"
                ));

        LocalDateTime now = LocalDateTime.now();

        Subscription subscription = new Subscription();

        subscription.setUser(user);
        subscription.setTier(SubscriptionTier.FREE);
        subscription.setActive(true);
        subscription.setCreatedAt(now);
        subscription.setUpdatedAt(now);

        return subscriptionRepository.save(subscription);
    }

    private List<String> getBenefits(
            SubscriptionTier tier
    ) {
        if (tier == SubscriptionTier.STUDENT_PREMIUM) {
            return List.of(
                    "Promovare gratuita 3 anunturi/luna",
                    "Acces prioritar la aplicatii joburi",
                    "Badge Student Premium pe profil"
            );
        }

        return List.of();
    }

    private BigDecimal getAmount(
            PaymentPurpose purpose,
            Listing listing
    ) {
        return switch (purpose) {

            case PREMIUM_SUB ->
                    new BigDecimal("19.99");

            case PROMOTED_LISTING -> {
                if (listing == null) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Anuntul este obligatoriu pentru promovare"
                    );
                }

                yield new BigDecimal("9.99");
            }

            case STUDY_MATERIAL -> {
                if (listing == null) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Anuntul este obligatoriu pentru materialul de studiu"
                    );
                }

                if (!listing.isRequiresPayment()) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Acest material nu necesita plata"
                    );
                }

                yield listing.getPrice();
            }
        };
    }

    private String getProductName(
            PaymentPurpose purpose
    ) {
        return switch (purpose) {

            case PREMIUM_SUB ->
                    "Student Premium";

            case PROMOTED_LISTING ->
                    "Promovare anunt";

            case STUDY_MATERIAL ->
                    "Material de studiu";
        };
    }

    private void validateUserRole(
            User user,
            PaymentPurpose purpose
    ) {
        if (purpose == PaymentPurpose.PREMIUM_SUB
                && user.getRole() != UserRole.STUDENT) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Doar studentii pot cumpara Student Premium"
            );
        }

        if ((purpose == PaymentPurpose.STUDY_MATERIAL
                || purpose == PaymentPurpose.PROMOTED_LISTING)
                && user.getRole() != UserRole.STUDENT) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Doar studentii pot efectua aceste plati"
            );
        }
    }

    private void validateListingForPayment(
            User user,
            Listing listing,
            PaymentPurpose purpose
    ) {
        if (purpose == PaymentPurpose.PREMIUM_SUB
                && listing != null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Student Premium nu necesita un anunt"
            );
        }

        if (purpose != PaymentPurpose.PREMIUM_SUB
                && listing == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Anuntul este obligatoriu pentru aceasta plata"
            );
        }

        if (listing == null) {
            return;
        }

        if (listing.getStatus() == null
                || listing.getStatus().name().equals("ARCHIVED")) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Anuntul nu mai este disponibil"
            );
        }

        if (purpose == PaymentPurpose.PROMOTED_LISTING
                && listing.isPromoted()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Anuntul este deja promovat"
            );
        }
    }
}