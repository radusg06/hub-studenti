package ro.hubstudentesc.web.marketplace;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.hubstudentesc.dto.common.PageEnvelopeDto;
import ro.hubstudentesc.dto.common.PaginationDto;
import ro.hubstudentesc.dto.marketplace.ListingCreateDto;
import ro.hubstudentesc.dto.marketplace.ListingCreateResponseDto;
import ro.hubstudentesc.dto.marketplace.ListingResponseDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.service.marketplace.ListingService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/marketplace/listings")
@RequiredArgsConstructor
public class MarketplaceController {

    private final ListingService listingService;

    @GetMapping
    public ResponseEntity<PageEnvelopeDto<ListingResponseDto>> getListings(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean promoted,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        int normalizedLimit = Math.max(1, Math.min(limit, 100));
        Page<ListingResponseDto> listings = listingService.findListings(
                categoryId,
                minPrice,
                maxPrice,
                location,
                promoted,
                PageRequest.of(Math.max(page, 1) - 1, normalizedLimit)
        );

        return ResponseEntity.ok(new PageEnvelopeDto<>(
                listings.getContent(),
                new PaginationDto(
                        listings.getNumber() + 1,
                        listings.getTotalPages(),
                        listings.getTotalElements()
                )
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponseDto> getListing(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                listingService.findById(id)
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListingCreateResponseDto> createListingFromJson(
            Authentication authentication,
            @RequestBody @Valid ListingCreateDto dto
    ) {
        User user = (User) authentication.getPrincipal();

        ListingResponseDto response =
                listingService.createListing(
                        user.getId(),
                        dto
                );

        return created(response.id());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ListingCreateResponseDto> createListing(
            Authentication authentication,

            @RequestPart("categoryId")
            Long categoryId,

            @RequestPart("title")
            String title,

            @RequestPart("description")
            String description,

            @RequestPart("price")
            BigDecimal price,

            @RequestPart("currency")
            String currency,

            @RequestPart(value = "condition", required = false)
            ro.hubstudentesc.enums.marketPlaceEnums.ListingCondition condition,

            @RequestPart("location")
            String location,

            @RequestPart("negotiable")
            boolean negotiable,

            @RequestPart("contactPhone")
            String contactPhone,

            @RequestPart("contactEmail")
            String contactEmail,

            @RequestPart("contactWhatsapp")
            boolean contactWhatsapp,

            @RequestPart(
                    value = "preferredContactInterval",
                    required = false
            )
            String preferredContactInterval,

            @RequestPart("requiresPayment")
            boolean requiresPayment,

            @RequestPart(
                    value = "imageUrls",
                    required = false
            )
            java.util.List<String> imageUrls,

            @RequestPart(
                    value = "materialPdf",
                    required = false
            )
            MultipartFile materialPdf
    ) {
        User user = (User) authentication.getPrincipal();

        ListingCreateDto dto = new ListingCreateDto(
                categoryId,
                title,
                description,
                price,
                currency,
                condition,
                location,
                negotiable,
                contactPhone,
                contactEmail,
                contactWhatsapp,
                preferredContactInterval,
                requiresPayment,
                imageUrls,
                materialPdf
        );

        ListingResponseDto response =
                listingService.createListing(
                        user.getId(),
                        dto
                );

        return created(response.id());
    }

    private ResponseEntity<ListingCreateResponseDto> created(UUID listingId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ListingCreateResponseDto(
                        true,
                        "Anuntul a fost publicat cu succes pe Bazar",
                        listingId
                ));
    }
}
