package ro.hubstudentesc.dto.jobs;

import ro.hubstudentesc.enums.jobEnums.JobApplicationStatus;

import java.util.UUID;

public record JobApplicationRecordDto(
        Long id,
        Long jobId,
        UUID userId,
        String cv,
        JobApplicationStatus status
) {
}
