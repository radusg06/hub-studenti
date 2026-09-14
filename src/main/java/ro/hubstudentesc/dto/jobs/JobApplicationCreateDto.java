package ro.hubstudentesc.dto.jobs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JobApplicationCreateDto(
        @NotBlank
        @Size(max = 1000)
        String cvUrl,

        @Size(max = 5000)
        String coverLetter
) {
}