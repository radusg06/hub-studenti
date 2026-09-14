package ro.hubstudentesc.dto.profiles;

import java.util.UUID;

public record CompanyProfileDto(
        UUID userId,
        String companyName,
        String cuiCif,
        String industry,
        String description,
        String websiteUrl,
        String logoUrl,
        String city,
        String address,
        String contactPhone
) {
}
