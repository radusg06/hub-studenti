package ro.hubstudentesc.dto.jobs;

import ro.hubstudentesc.enums.jobEnums.JobStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record JobResponseDto(
        boolean success,
        String message,
        UUID id,
        String title,
        JobStatus status,
        LocalDateTime expiresAt
) {
}
