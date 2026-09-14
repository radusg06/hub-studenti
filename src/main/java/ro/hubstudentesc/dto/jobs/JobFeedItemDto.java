package ro.hubstudentesc.dto.jobs;

import ro.hubstudentesc.enums.jobEnums.JobType;
import ro.hubstudentesc.enums.jobEnums.WorkplaceType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record JobFeedItemDto(
        UUID id,
        String title,
        JobCompanyDto company,
        JobType type,
        WorkplaceType workplace,
        String location,
        List<String> requiredSkills,
        JobSalaryDto salary,
        LocalDateTime createdAt
) {
}
