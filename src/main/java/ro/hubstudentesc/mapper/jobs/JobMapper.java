package ro.hubstudentesc.mapper.jobs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.hubstudentesc.dto.jobs.JobCreateDto;
import ro.hubstudentesc.dto.jobs.JobResponseDto;
import ro.hubstudentesc.persistence.entity.jobs.Job;

@Mapper(componentModel = "spring")
public interface JobMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Job toEntity(JobCreateDto dto);

    JobResponseDto toDto(Job job);
}
