package ro.hubstudentesc.service;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.JobApplicationRecordDto;
import ro.hubstudentesc.enums.JobApplicationStatus;
import ro.hubstudentesc.persistence.entity.Job;
import ro.hubstudentesc.persistence.entity.JobApplication;
import ro.hubstudentesc.persistence.entity.auth.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class JobApplicationMapperImpl implements JobApplicationMapper {

    @Override
    public JobApplicationRecordDto toDto(JobApplication jobApplication) {
        if ( jobApplication == null ) {
            return null;
        }

        Long jobId = null;
        UUID userId = null;
        Long id = null;
        String cv = null;
        JobApplicationStatus status = null;

        jobId = jobApplicationJobId( jobApplication );
        userId = jobApplicationUserId( jobApplication );
        id = jobApplication.getId();
        cv = jobApplication.getCv();
        status = jobApplication.getStatus();

        JobApplicationRecordDto jobApplicationRecordDto = new JobApplicationRecordDto( id, jobId, userId, cv, status );

        return jobApplicationRecordDto;
    }

    private Long jobApplicationJobId(JobApplication jobApplication) {
        Job job = jobApplication.getJob();
        if ( job == null ) {
            return null;
        }
        return job.getId();
    }

    private UUID jobApplicationUserId(JobApplication jobApplication) {
        User user = jobApplication.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
