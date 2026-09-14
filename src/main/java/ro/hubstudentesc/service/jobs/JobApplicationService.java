package ro.hubstudentesc.service.jobs;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.dto.jobs.JobApplicationCreateDto;
import ro.hubstudentesc.dto.jobs.JobApplicationResponseDto;
import ro.hubstudentesc.dto.jobs.JobApplicationStatusDto;
import ro.hubstudentesc.enums.authEnums.UserRole;
import ro.hubstudentesc.enums.jobEnums.JobApplicationStatus;
import ro.hubstudentesc.enums.jobEnums.JobStatus;
import ro.hubstudentesc.exception.JobApplicationAlreadyExistsException;
import ro.hubstudentesc.persistence.entity.jobs.Job;
import ro.hubstudentesc.persistence.entity.jobs.JobApplication;
import ro.hubstudentesc.persistence.entity.profiles.CompanyProfile;
import ro.hubstudentesc.persistence.entity.profiles.StudentProfile;
import ro.hubstudentesc.persistence.repository.jobs.JobApplicationRepository;
import ro.hubstudentesc.persistence.repository.jobs.JobRepository;
import ro.hubstudentesc.persistence.repository.profiles.CompanyProfileRepository;
import ro.hubstudentesc.persistence.repository.profiles.StudentProfileRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final CompanyProfileRepository companyProfileRepository;

    @Transactional
    public JobApplicationResponseDto addApplication(
            UUID jobId,
            UUID studentUserId,
            JobApplicationCreateDto dto
    ) {

        StudentProfile student = studentProfileRepository.findById(studentUserId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Utilizatorul nu are profil de student"
                ));

        if (student.getUser() == null
                || student.getUser().getRole() != UserRole.STUDENT) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Doar utilizatorii de tip STUDENT pot aplica la joburi"
            );
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Jobul nu exista"
                ));

        if (job.getStatus() != JobStatus.ACTIVE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nu se poate aplica la acest job deoarece nu este activ"
            );
        }

        if (job.getExpiresAt() != null
                && job.getExpiresAt().isBefore(LocalDateTime.now())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nu se mai poate aplica la acest job deoarece a expirat"
            );
        }

        if (jobApplicationRepository.existsByJob_IdAndStudent_UserId(
                jobId,
                studentUserId
        )) {
            throw new JobApplicationAlreadyExistsException();
        }

        JobApplication application = new JobApplication();

        application.setJob(job);
        application.setStudent(student);

        /*
         * Salvam CV-ul exact asa cum era in momentul aplicarii.
         * Acesta reprezinta snapshot-ul CV-ului pentru candidatura.
         */
        application.setCvUrl(dto.cvUrl());
        application.setCoverLetter(dto.coverLetter());

        application.setStatus(JobApplicationStatus.APPLIED);

        LocalDateTime now = LocalDateTime.now();

        application.setAppliedAt(now);
        application.setUpdatedAt(now);

        jobApplicationRepository.save(application);

        return new JobApplicationResponseDto(
                true,
                "Candidatura a fost transmisa cu succes catre companie",
                application.getId(),
                application.getStatus()
        );
    }

    @Transactional
    public JobApplicationResponseDto updateStatus(
            UUID applicationId,
            UUID companyUserId,
            JobApplicationStatusDto dto
    ) {

        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Candidatura nu exista"
                ));

        CompanyProfile company = companyProfileRepository.findById(companyUserId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Utilizatorul nu are profil de companie"
                ));

        if (company.getUser() == null
                || company.getUser().getRole() != UserRole.EMPLOYER) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Doar utilizatorii de tip EMPLOYER pot modifica statusul candidaturilor"
            );
        }

        Job job = application.getJob();

        if (job == null
                || job.getCompany() == null
                || !job.getCompany()
                .getUserId()
                .equals(company.getUserId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Nu ai permisiunea sa modifici aceasta candidatura"
            );
        }

        application.setStatus(dto.status());
        application.setRecruiterNotes(dto.recruiterNotes());
        application.setUpdatedAt(LocalDateTime.now());

        jobApplicationRepository.save(application);

        return new JobApplicationResponseDto(
                true,
                "Statusul candidaturii a fost actualizat",
                application.getId(),
                application.getStatus()
        );
    }
}
