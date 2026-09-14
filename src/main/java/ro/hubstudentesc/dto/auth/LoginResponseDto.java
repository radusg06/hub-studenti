package ro.hubstudentesc.dto.auth;

public record LoginResponseDto(
        String accessToken,
        String refreshToken,
        long expiresIn,
        UserProfileResponseDto user
) {
}
