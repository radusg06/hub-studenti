package ro.hubstudentesc.dto.jobs;

import ro.hubstudentesc.enums.jobEnums.JobApplicationStatus;

import java.util.UUID;

public record JobApplicationResponseDto(
        boolean success,
        String message,
        UUID applicationId,
        JobApplicationStatus status
) {
}
