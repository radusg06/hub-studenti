package ro.hubstudentesc.dto.marketplace;

public record ListingContactDto(
        String phone,
        String email,
        boolean whatsappAvailable,
        String preferredInterval
) {
}
