package ro.hubstudentesc.service.jobs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.dto.jobs.JobCreateDto;
import ro.hubstudentesc.dto.jobs.JobCompanyDto;
import ro.hubstudentesc.dto.jobs.JobFeedItemDto;
import ro.hubstudentesc.dto.jobs.JobResponseDto;
import ro.hubstudentesc.dto.jobs.JobSalaryDto;
import ro.hubstudentesc.enums.authEnums.UserRole;
import ro.hubstudentesc.enums.jobEnums.JobStatus;
import ro.hubstudentesc.enums.jobEnums.JobType;
import ro.hubstudentesc.mapper.jobs.JobMapper;
import ro.hubstudentesc.persistence.entity.jobs.Job;
import ro.hubstudentesc.persistence.entity.profiles.CompanyProfile;
import ro.hubstudentesc.persistence.repository.jobs.JobRepository;
import ro.hubstudentesc.persistence.repository.profiles.CompanyProfileRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final CompanyProfileRepository companyProfileRepository;

    public JobResponseDto addJob(UUID userId, JobCreateDto dto) {

        CompanyProfile company = getCompanyForUser(userId);

        Job job = jobMapper.toEntity(dto);

        job.setCompany(company);
        job.setStatus(JobStatus.ACTIVE);

        LocalDateTime now = LocalDateTime.now();

        job.setExpiresAt(now.plusDays(30));
        job.setCreatedAt(now);
        job.setUpdatedAt(now);

        jobRepository.save(job);

        return new JobResponseDto(
                true,
                "Anuntul de job a fost publicat cu succes",
                job.getId(),
                job.getTitle(),
                job.getStatus(),
                job.getExpiresAt()
        );
    }

    public Page<Job> findAll(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    public Page<Job> findByLocation(
            String location,
            Pageable pageable
    ) {
        return jobRepository.findByLocation(location, pageable);
    }

    public Page<Job> findByType(
            JobType type,
            Pageable pageable
    ) {
        return jobRepository.findByType(type, pageable);
    }

    public Page<JobFeedItemDto> findJobs(
            String location,
            String type,
            String[] skills,
            Pageable pageable
    ) {
        return jobRepository.findJobs(
                location,
                type,
                skills,
                pageable
        ).map(this::toFeedItem);
    }

    public JobResponseDto updateJob(
            UUID id,
            UUID userId,
            JobCreateDto dto
    ) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Jobul nu exista"
                ));

        CompanyProfile company = getCompanyForUser(userId);

        verifyOwnership(job, company);

        job.setTitle(dto.title());
        job.setDescription(dto.description());
        job.setRequirements(dto.requirements());
        job.setType(dto.type());
        job.setWorkplace(dto.workplace());
        job.setLocation(dto.location());
        job.setExperienceLevel(dto.experienceLevel());
        job.setRequiredSkills(dto.requiredSkills());
        job.setSalaryMin(dto.salaryMin());
        job.setSalaryMax(dto.salaryMax());
        job.setSalaryCurrency(dto.salaryCurrency());
        job.setUpdatedAt(LocalDateTime.now());

        jobRepository.save(job);

        return new JobResponseDto(
                true,
                "Anuntul de job a fost actualizat cu succes",
                job.getId(),
                job.getTitle(),
                job.getStatus(),
                job.getExpiresAt()
        );
    }

    public void deleteJob(
            UUID id,
            UUID userId
    ) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Jobul nu exista"
                ));

        CompanyProfile company = getCompanyForUser(userId);

        verifyOwnership(job, company);

        jobRepository.delete(job);
    }

    private CompanyProfile getCompanyForUser(UUID userId) {

        CompanyProfile company = companyProfileRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "HUB_JOBS_01"
                ));

        if (company.getUser() == null
                || company.getUser().getRole() != UserRole.EMPLOYER) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "HUB_JOBS_01"
            );
        }

        return company;
    }

    private void verifyOwnership(
            Job job,
            CompanyProfile company
    ) {

        if (job.getCompany() == null
                || !job.getCompany()
                .getUserId()
                .equals(company.getUserId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Nu ai permisiunea sa modifici acest job"
            );
        }
    }

    private JobFeedItemDto toFeedItem(Job job) {
        CompanyProfile company = job.getCompany();

        return new JobFeedItemDto(
                job.getId(),
                job.getTitle(),
                new JobCompanyDto(
                        company.getCompanyName(),
                        company.getCity(),
                        company.getLogoUrl()
                ),
                job.getType(),
                job.getWorkplace(),
                job.getLocation(),
                job.getRequiredSkills() == null
                        ? List.of()
                        : List.of(job.getRequiredSkills()),
                new JobSalaryDto(
                        job.getSalaryMin(),
                        job.getSalaryMax(),
                        job.getSalaryCurrency()
                ),
                job.getCreatedAt()
        );
    }
}
