package ro.hubstudentesc.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.JobApplicationRecordDto;
import ro.hubstudentesc.dto.JobApplicationStatusDto;
import ro.hubstudentesc.enums.JobApplicationStatus;
import ro.hubstudentesc.exception.JobApplicationAlreadyExistsException;
import ro.hubstudentesc.persistence.entity.Job;
import ro.hubstudentesc.persistence.entity.JobApplication;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.repository.JobApplicationRepository;
import ro.hubstudentesc.persistence.repository.JobRepository;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobApplicationMapper jobApplicationMapper;

    public void addApplication(JobApplicationRecordDto dto){
        if(jobApplicationRepository.existsByJobIdAndUserId(dto.jobId(), dto.userId())){
            throw new JobApplicationAlreadyExistsException();
        }

        Job job =  jobRepository.findById(dto.jobId()).orElseThrow();
        User user = userRepository.findById(dto.userId()).orElseThrow();

        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setUser(user);
        application.setCv(dto.cv());
        application.setStatus(dto.status());

        jobApplicationRepository.save(application);

    }

    public Page<JobApplicationRecordDto> findAll(Pageable pageable) {
        return jobApplicationRepository.findAll(pageable).map(jobApplicationMapper::toDto);
    }

    public void updateApplication(Long id, JobApplicationRecordDto dto) {
        JobApplication application = jobApplicationRepository.findById(id).orElseThrow();
        Job job = jobRepository.findById(dto.jobId()).orElseThrow();
        User user = userRepository.findById(dto.userId()).orElseThrow();
        application.setJob(job);
        application.setUser(user);
        application.setCv(dto.cv());
        application.setStatus(dto.status());

        jobApplicationRepository.save(application);

    }

    public void deleteApplication(Long id){
        JobApplication application = jobApplicationRepository.findById(id).orElseThrow();
        jobApplicationRepository.delete(application);
    }

    public void updateStatus(Long id, JobApplicationStatusDto dto){
        JobApplication application = jobApplicationRepository.findById(id).orElseThrow();
        application.setStatus(dto.status());

        jobApplicationRepository.save(application);
    }
}
