package ro.hubstudentesc.web.profiles;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.profiles.CompanyProfileDto;
import ro.hubstudentesc.dto.profiles.CurrentProfileDto;
import ro.hubstudentesc.dto.profiles.StudentProfileDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.service.profiles.ProfileService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<CurrentProfileDto> getCurrentProfile(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                profileService.getCurrentProfile(user.getId())
        );
    }

    @PutMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentProfileDto> upsertStudentProfile(
            Authentication authentication,
            @RequestBody @Valid StudentProfileDto dto
    ) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                profileService.upsertStudentProfile(user.getId(), dto)
        );
    }

    @PutMapping("/company")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<CompanyProfileDto> upsertCompanyProfile(
            Authentication authentication,
            @RequestBody @Valid CompanyProfileDto dto
    ) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                profileService.upsertCompanyProfile(user.getId(), dto)
        );
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentProfileDto> getPublicStudentProfile(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                profileService.getPublicStudentProfile(id)
        );
    }
}
