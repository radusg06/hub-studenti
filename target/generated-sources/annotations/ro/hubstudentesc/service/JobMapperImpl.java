package ro.hubstudentesc.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.JobRecordDto;
import ro.hubstudentesc.persistence.entity.Job;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class JobMapperImpl implements JobMapper {

    @Override
    public JobRecordDto toDto(Job job) {
        if ( job == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String company = null;
        String description = null;
        String city = null;
        Double salary = null;
        String program = null;

        id = job.getId();
        title = job.getTitle();
        company = job.getCompany();
        description = job.getDescription();
        city = job.getCity();
        salary = job.getSalary();
        program = job.getProgram();

        JobRecordDto jobRecordDto = new JobRecordDto( id, title, company, description, city, salary, program );

        return jobRecordDto;
    }

    @Override
    public List<JobRecordDto> toDto(List<Job> jobs) {
        if ( jobs == null ) {
            return null;
        }

        List<JobRecordDto> list = new ArrayList<JobRecordDto>( jobs.size() );
        for ( Job job : jobs ) {
            list.add( toDto( job ) );
        }

        return list;
    }

    @Override
    public Job toEntity(JobRecordDto dto) {
        if ( dto == null ) {
            return null;
        }

        Job job = new Job();

        job.setId( dto.id() );
        job.setTitle( dto.title() );
        job.setCompany( dto.company() );
        job.setDescription( dto.description() );
        job.setCity( dto.city() );
        job.setSalary( dto.salary() );
        job.setProgram( dto.program() );

        return job;
    }
}
