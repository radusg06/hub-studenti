package ro.hubstudentesc.dto;

import ro.hubstudentesc.enums.JobApplicationStatus;

import java.util.UUID;

public record JobApplicationRecordDto(
        Long id,
        Long jobId,
        UUID userId,
        String cv,
        JobApplicationStatus status
) {
}
