package ro.hubstudentesc.service.jobs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.persistence.entity.jobs.Job;
import ro.hubstudentesc.persistence.entity.jobs.SavedJob;
import ro.hubstudentesc.persistence.entity.profiles.StudentProfile;
import ro.hubstudentesc.persistence.repository.jobs.JobRepository;
import ro.hubstudentesc.persistence.repository.jobs.SavedJobRepository;
import ro.hubstudentesc.persistence.repository.profiles.StudentProfileRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final JobRepository jobRepository;
    private final StudentProfileRepository studentProfileRepository;

    public void saveJob(UUID userId, UUID jobId) {
        if (savedJobRepository.existsByUser_UserIdAndJob_Id(userId, jobId)) {
            return;
        }

        StudentProfile user = studentProfileRepository.findById(userId).orElseThrow();
        Job job = jobRepository.findById(jobId).orElseThrow();

        SavedJob savedJob = new SavedJob();
        savedJob.setUser(user);
        savedJob.setJob(job);
        savedJob.setCreatedAt(LocalDateTime.now());

        savedJobRepository.save(savedJob);
    }

    public void deleteSavedJob(UUID userId, UUID jobId) {
        savedJobRepository.deleteByUser_UserIdAndJob_Id(userId, jobId);
    }
}
