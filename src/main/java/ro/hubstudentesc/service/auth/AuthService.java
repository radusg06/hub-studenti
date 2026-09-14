package ro.hubstudentesc.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.dto.auth.LoginResponseDto;
import ro.hubstudentesc.dto.auth.RegisterRequestDto;
import ro.hubstudentesc.dto.auth.UserProfileResponseDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.auth.UserCredential;
import ro.hubstudentesc.persistence.repository.auth.UserCredentialRepository;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;
import ro.hubstudentesc.security.JwtService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public void register(RegisterRequestDto request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "HUB_AUTH_04"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setEmail(request.email());
        user.setSub(request.email().toLowerCase());
        user.setGivenName(request.firstName());
        user.setFamilyName(request.lastName());
        user.setRole(request.role());
        user.setIsActive(true);
        user.setIsVerified(false);
        user.setEmailVerified(false);
        user.setPhoneNumberVerified(false);
        user.setFailedLoginAttempts(0);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        userRepository.save(user);

        UserCredential credential = new UserCredential();
        credential.setUserId(user.getId());
        credential.setPasswordHash(passwordEncoder.encode(request.password()));
        credential.setAlgorithm("argon2id");
        credential.setPasswordVersion(1);
        credential.setMustChange(false);
        credential.setPasswordChangedAt(now);
        credential.setCreatedAt(now);
        credential.setUpdatedAt(now);

        userCredentialRepository.save(credential);
    }

    @Transactional
    public LoginResponseDto login(String email, String password) {

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "HUB_AUTH_01"
                        )
                );

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "HUB_AUTH_01"
            );
        }

        UserCredential credential = userCredentialRepository
                .findById(user.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "HUB_AUTH_01"
                        )
                );

        if (!passwordEncoder.matches(password, credential.getPasswordHash())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "HUB_AUTH_01"
            );
        }

        user.setLastLoginAt(LocalDateTime.now());
        user.setFailedLoginAttempts(0);
        user.setUpdatedAt(LocalDateTime.now());

        String accessToken = jwtService.generateToken(user);

        return new LoginResponseDto(
                accessToken,
                UUID.randomUUID().toString(),
                3600,
                toProfile(user)
        );
    }

    public UserProfileResponseDto getCurrentUser(User user) {
        return toProfile(user);
    }

    private UserProfileResponseDto toProfile(User user) {
        return new UserProfileResponseDto(
                user.getId(),
                user.getEmail(),
                user.getGivenName(),
                user.getFamilyName(),
                user.getRole(),
                Boolean.TRUE.equals(user.getIsVerified())
        );
    }
}
