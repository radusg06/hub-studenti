package ro.hubstudentesc.dto.auth;

import ro.hubstudentesc.enums.authEnums.UserRole;

import java.util.UUID;

public record UserProfileResponseDto(
        UUID userId,
        String email,
        String firstName,
        String lastName,
        UserRole role,
        boolean isVerified
) {
}
