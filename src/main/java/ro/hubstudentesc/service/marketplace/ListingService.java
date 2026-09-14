package ro.hubstudentesc.service.marketplace;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.dto.marketplace.ListingCreateDto;
import ro.hubstudentesc.dto.marketplace.ListingContactDto;
import ro.hubstudentesc.dto.marketplace.ListingResponseDto;
import ro.hubstudentesc.dto.marketplace.ListingSellerDto;
import ro.hubstudentesc.enums.marketPlaceEnums.ListingCondition;
import ro.hubstudentesc.enums.marketPlaceEnums.ListingStatus;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.marketplace.Category;
import ro.hubstudentesc.persistence.entity.marketplace.Listing;
import ro.hubstudentesc.persistence.entity.marketplace.ListingImage;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;
import ro.hubstudentesc.persistence.repository.marketplace.CategoryRepository;
import ro.hubstudentesc.persistence.repository.marketplace.ListingImageRepository;
import ro.hubstudentesc.persistence.repository.marketplace.ListingRepository;
import ro.hubstudentesc.service.azure.AzureBlobService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingImageRepository listingImageRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AzureBlobService azureBlobService;

    public ListingResponseDto createListing(
            UUID sellerId,
            ListingCreateDto dto
    ) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilizatorul nu exista"
                ));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria nu exista"
                ));

        if (dto.price() == null
                || dto.price().compareTo(BigDecimal.ZERO) < 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Pretul nu poate fi negativ"
            );
        }

        MultipartFile materialPdf = dto.materialPdf();

        if (materialPdf != null && !materialPdf.isEmpty()) {
            validatePdf(materialPdf);
        }

        if (dto.requiresPayment()
                && (materialPdf == null || materialPdf.isEmpty())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Un material care necesita plata trebuie sa aiba un fisier PDF"
            );
        }

        Listing listing = new Listing();

        listing.setSeller(seller);
        listing.setCategory(category);
        listing.setTitle(dto.title());
        listing.setDescription(dto.description());
        listing.setPrice(dto.price());
        listing.setCurrency(dto.currency());
        listing.setNegotiable(dto.negotiable());

        listing.setCondition(
                dto.condition() != null
                        ? dto.condition()
                        : ListingCondition.USED
        );

        listing.setStatus(ListingStatus.ACTIVE);
        listing.setLocation(dto.location());
        listing.setViewsCount(0);
        listing.setPromoted(false);
        listing.setPromotedUntil(null);

        listing.setRequiresPayment(dto.requiresPayment());

        listing.setContactPhone(dto.contactPhone());
        listing.setContactEmail(dto.contactEmail());
        listing.setContactWhatsapp(dto.contactWhatsapp());
        listing.setPreferredContactInterval(
                dto.preferredContactInterval()
        );

        LocalDateTime now = LocalDateTime.now();

        listing.setCreatedAt(now);
        listing.setUpdatedAt(now);

        /*
         * Salvam mai intai anuntul pentru a avea ID-ul lui.
         */
        listingRepository.save(listing);

        /*
         * Daca exista PDF, il incarcam in containerul privat
         * marketplace-materials.
         *
         * In baza de date salvam numele blob-ului,
         * nu un URL public.
         */
        if (materialPdf != null && !materialPdf.isEmpty()) {

            String blobName =
                    azureBlobService.uploadMarketplacePdf(materialPdf);

            listing.setMaterialPdfUrl(blobName);
            listing.setUpdatedAt(LocalDateTime.now());

            listingRepository.save(listing);
        }

        /*
         * Salvam imaginile anuntului.
         */
        if (dto.imageUrls() != null
                && !dto.imageUrls().isEmpty()) {

            for (int i = 0; i < dto.imageUrls().size(); i++) {

                String imageUrl = dto.imageUrls().get(i);

                if (imageUrl == null || imageUrl.isBlank()) {
                    continue;
                }

                ListingImage image = new ListingImage();

                image.setListing(listing);
                image.setImageUrl(imageUrl);
                image.setDisplayOrder((short) i);
                image.setCreatedAt(now);

                listingImageRepository.save(image);
            }
        }

        return toResponse(listing);
    }

    public Page<ListingResponseDto> findListings(
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String location,
            Boolean promoted,
            Pageable pageable
    ) {
        if (minPrice != null
                && maxPrice != null
                && minPrice.compareTo(maxPrice) > 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Pretul minim nu poate fi mai mare decat pretul maxim"
            );
        }

        return listingRepository
                .findListings(
                        categoryId,
                        minPrice,
                        maxPrice,
                        location,
                        promoted,
                        ListingStatus.ARCHIVED,
                        pageable
                )
                .map(this::toResponse);
    }

    public ListingResponseDto findById(UUID id) {

        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "HUB_MKT_01"
                ));

        listing.setViewsCount(
                listing.getViewsCount() + 1
        );

        listing.setUpdatedAt(
                LocalDateTime.now()
        );

        listingRepository.save(listing);

        return toResponse(listing);
    }

    private void validatePdf(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType == null
                || !contentType.equalsIgnoreCase("application/pdf")) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Este permis doar formatul PDF"
            );
        }

        String filename = file.getOriginalFilename();

        if (filename == null
                || !filename.toLowerCase().endsWith(".pdf")) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Fisierul trebuie sa aiba extensia .pdf"
            );
        }

        /*
         * Limita de 20 MB pentru materialele PDF.
         */
        long maxSize = 20L * 1024L * 1024L;

        if (file.getSize() > maxSize) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Fisierul PDF nu poate depasi 20 MB"
            );
        }
    }

    private ListingResponseDto toResponse(
            Listing listing
    ) {

        String sellerName =
                listing.getSeller().getGivenName()
                        + " "
                        + listing.getSeller().getFamilyName();

        List<String> images = listingImageRepository
                .findByListing_IdOrderByDisplayOrder(
                        listing.getId()
                )
                .stream()
                .map(ListingImage::getImageUrl)
                .toList();

        return new ListingResponseDto(
                listing.getId(),
                listing.getTitle(),
                listing.getDescription(),
                listing.getCategory().getName(),
                listing.getPrice(),
                listing.getCurrency(),
                listing.getCondition(),
                listing.getStatus(),
                listing.getLocation(),
                listing.isNegotiable(),
                listing.isPromoted(),
                listing.isRequiresPayment(),
                new ListingSellerDto(
                        listing.getSeller().getId(),
                        sellerName
                ),
                new ListingContactDto(
                        listing.getContactPhone(),
                        listing.getContactEmail(),
                        listing.isContactWhatsapp(),
                        listing.getPreferredContactInterval()
                ),
                images,
                listing.getViewsCount(),
                listing.getCreatedAt()
        );
    }
}
