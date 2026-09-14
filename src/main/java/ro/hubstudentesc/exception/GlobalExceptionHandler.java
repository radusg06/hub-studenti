package ro.hubstudentesc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Pattern ERROR_CODE_PATTERN =
            Pattern.compile("^(HUB|UUMDSIN)_[A-Z0-9_]+$");

    @ExceptionHandler(JobApplicationAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleJobApplicationAlreadyExists(
            JobApplicationAlreadyExistsException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error("HUB_JOBS_02", exception.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(
            ResponseStatusException exception
    ) {
        HttpStatus status = HttpStatus.valueOf(exception.getStatusCode().value());
        String reason = exception.getReason();

        if (reason != null && ERROR_CODE_PATTERN.matcher(reason).matches()) {
            return ResponseEntity
                    .status(status)
                    .body(error(reason, defaultMessage(reason)));
        }

        return ResponseEntity
                .status(status)
                .body(Map.of(
                        "success", false,
                        "message", reason == null ? status.getReasonPhrase() : reason,
                        "status", status.value(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException exception
    ) {
        return ResponseEntity
                .badRequest()
                .body(error("UUMDSIN_07", null));
    }

    private Map<String, Object> error(String code, String message) {
        if (message == null || message.isBlank()) {
            return Map.of("error", Map.of("code", code));
        }

        return Map.of(
                "error",
                Map.of(
                        "code", code,
                        "message", message
                )
        );
    }

    private String defaultMessage(String code) {
        return switch (code) {
            case "HUB_JOBS_01" ->
                    "Doar companiile verificate pot publica anunturi de joburi";
            case "HUB_MKT_01" ->
                    "Anuntul solicitat nu exista sau a fost arhivat";
            case "HUB_FAC_01" ->
                    "Ai lasat deja o recenzie pentru aceasta facultate";
            default -> null;
        };
    }
}
