package ro.hubstudentesc.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import ro.hubstudentesc.enums.authEnums.UserRole;

import java.util.UUID;

public record UserRecordDto(
    UUID id,

    @NotBlank
    String givenName,

    @NotBlank
    String familyName,

    @NotBlank
    String sub,

    @NotBlank
    @Email
    String email,

    String picture,

    UserRole role,

    Boolean isVerified,

    Boolean isActive
){
}
