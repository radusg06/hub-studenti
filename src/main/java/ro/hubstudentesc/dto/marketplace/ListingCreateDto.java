package ro.hubstudentesc.dto.marketplace;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.multipart.MultipartFile;
import ro.hubstudentesc.enums.marketPlaceEnums.ListingCondition;

import java.math.BigDecimal;
import java.util.List;

public record ListingCreateDto(

        @NotNull
        Long categoryId,

        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotNull
        @PositiveOrZero
        BigDecimal price,

        @NotBlank
        String currency,

        ListingCondition condition,

        @NotBlank
        String location,

        @JsonAlias("isNegotiable")
        boolean negotiable,

        @NotBlank
        String contactPhone,

        @NotBlank
        @Email
        String contactEmail,

        boolean contactWhatsapp,

        String preferredContactInterval,

        boolean requiresPayment,

        List<String> imageUrls,

        MultipartFile materialPdf
) {
}
