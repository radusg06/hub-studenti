package ro.hubstudentesc.web.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.auth.RegisterRequestDto;
import ro.hubstudentesc.dto.auth.RegisterResponseDto;
import ro.hubstudentesc.dto.auth.LoginRequestDto;
import ro.hubstudentesc.dto.auth.LoginResponseDto;
import ro.hubstudentesc.dto.auth.UserProfileResponseDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.service.auth.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody @Valid LoginRequestDto request
    ) {
        return ResponseEntity.ok(
                authService.login(
                        request.email(),
                        request.password()
                )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(
            @RequestBody @Valid RegisterRequestDto request
    ) {
        authService.register(request);

        return ResponseEntity.status(201).body(
                new RegisterResponseDto("User registered successfully")
        );
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDto> me(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(authService.getCurrentUser(user));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }
}
