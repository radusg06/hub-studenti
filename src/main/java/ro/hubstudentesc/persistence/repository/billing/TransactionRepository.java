package ro.hubstudentesc.persistence.repository.billing;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.enums.billing.PaymentStatus;
import ro.hubstudentesc.persistence.entity.billing.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Optional<Transaction> findByPaymentGatewayRef(
            String paymentGatewayRef
    );

    Optional<Transaction> findByUser_IdAndListing_IdAndStatus(
            UUID userId,
            UUID listingId,
            PaymentStatus status
    );
}