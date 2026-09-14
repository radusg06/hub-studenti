package ro.hubstudentesc.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ro.hubstudentesc.enums.authEnums.UserRole;

public record RegisterRequestDto(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotNull
        UserRole role
) {
}
