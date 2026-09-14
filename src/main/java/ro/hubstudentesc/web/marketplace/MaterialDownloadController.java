package ro.hubstudentesc.web.marketplace;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.service.marketplace.MarketplaceMaterialService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/marketplace/listings")
@RequiredArgsConstructor
public class MaterialDownloadController {

    private final MarketplaceMaterialService marketplaceMaterialService;

    @GetMapping("/{listingId}/material")
    public ResponseEntity<Map<String, String>> getMaterialDownloadUrl(
            Authentication authentication,
            @PathVariable UUID listingId
    ) {
        User user = (User) authentication.getPrincipal();

        String downloadUrl =
                marketplaceMaterialService.getMaterialDownloadUrl(
                        listingId,
                        user.getId()
                );

        return ResponseEntity.ok(
                Map.of(
                        "downloadUrl", downloadUrl
                )
        );
    }
}