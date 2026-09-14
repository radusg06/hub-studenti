package ro.hubstudentesc.dto.jobs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ro.hubstudentesc.enums.jobEnums.JobApplicationStatus;

public record JobApplicationStatusDto(
        @NotNull
        JobApplicationStatus status,

        @Size(max = 2000)
        String recruiterNotes
) {
}