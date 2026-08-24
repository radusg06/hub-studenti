package ro.hubstudentesc.dto.bazar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BazarRecordDto(
        @NotNull
        UUID userId,

        @NotBlank
        @Size(max = 100)
        String title,

        @NotBlank
        @Size(max = 5000)
        String description,

        @NotNull
        @PositiveOrZero
        BigDecimal price,

        @NotNull
        LocalDateTime createdAt
) {
}
