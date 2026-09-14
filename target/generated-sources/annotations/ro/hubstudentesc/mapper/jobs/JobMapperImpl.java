package ro.hubstudentesc.mapper.jobs;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.jobs.JobCreateDto;
import ro.hubstudentesc.dto.jobs.JobResponseDto;
import ro.hubstudentesc.enums.jobEnums.JobStatus;
import ro.hubstudentesc.persistence.entity.jobs.Job;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-14T14:49:04+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class JobMapperImpl implements JobMapper {

    @Override
    public Job toEntity(JobCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        Job job = new Job();

        job.setTitle( dto.title() );
        job.setDescription( dto.description() );
        job.setRequirements( dto.requirements() );
        job.setType( dto.type() );
        job.setWorkplace( dto.workplace() );
        job.setLocation( dto.location() );
        job.setExperienceLevel( dto.experienceLevel() );
        String[] requiredSkills = dto.requiredSkills();
        if ( requiredSkills != null ) {
            job.setRequiredSkills( Arrays.copyOf( requiredSkills, requiredSkills.length ) );
        }
        job.setSalaryMin( dto.salaryMin() );
        job.setSalaryMax( dto.salaryMax() );
        job.setSalaryCurrency( dto.salaryCurrency() );

        return job;
    }

    @Override
    public JobResponseDto toDto(Job job) {
        if ( job == null ) {
            return null;
        }

        UUID id = null;
        String title = null;
        JobStatus status = null;
        LocalDateTime expiresAt = null;

        id = job.getId();
        title = job.getTitle();
        status = job.getStatus();
        expiresAt = job.getExpiresAt();

        boolean success = false;
        String message = null;

        JobResponseDto jobResponseDto = new JobResponseDto( success, message, id, title, status, expiresAt );

        return jobResponseDto;
    }
}
