package ro.hubstudentesc.service.marketplace;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.persistence.entity.billing.Transaction;
import ro.hubstudentesc.persistence.entity.marketplace.Listing;
import ro.hubstudentesc.persistence.repository.billing.TransactionRepository;
import ro.hubstudentesc.persistence.repository.marketplace.ListingRepository;
import ro.hubstudentesc.service.azure.AzureBlobService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarketplaceMaterialService {

    private final ListingRepository listingRepository;
    private final TransactionRepository transactionRepository;
    private final AzureBlobService azureBlobService;

    public String getMaterialDownloadUrl(
            UUID listingId,
            UUID userId
    ) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Anuntul nu exista"
                ));

        if (listing.getMaterialPdfUrl() == null
                || listing.getMaterialPdfUrl().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Acest anunt nu are material PDF"
            );
        }

        if (!listing.isRequiresPayment()) {
            return azureBlobService.generateMarketplaceReadSasUrl(
                    listing.getMaterialPdfUrl(),
                    15
            );
        }

        boolean hasCompletedTransaction =
                transactionRepository
                        .findByUser_IdAndListing_IdAndStatus(
                                userId,
                                listingId,
                                ro.hubstudentesc.enums.billing.PaymentStatus.COMPLETED
                        )
                        .isPresent();

        if (!hasCompletedTransaction) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Trebuie sa achizitionezi materialul inainte de descarcare"
            );
        }

        return azureBlobService.generateMarketplaceReadSasUrl(
                listing.getMaterialPdfUrl(),
                15
        );
    }
}