package ro.hubstudentesc.dto.jobs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ro.hubstudentesc.enums.jobEnums.JobType;
import ro.hubstudentesc.enums.jobEnums.WorkplaceType;

import java.math.BigDecimal;

public record JobCreateDto(
        @NotBlank
        String title,

        @NotBlank
        String description,

        String requirements,

        @NotNull
        JobType type,

        @NotNull
        WorkplaceType workplace,

        @NotBlank
        String location,

        @NotBlank
        String experienceLevel,

        @NotNull
        String[] requiredSkills,

        BigDecimal salaryMin,

        BigDecimal salaryMax,

        String salaryCurrency
) {
}