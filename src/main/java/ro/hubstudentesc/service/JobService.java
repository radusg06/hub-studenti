package ro.hubstudentesc.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.JobRecordDto;
import ro.hubstudentesc.exception.JobNotFoundException;
import ro.hubstudentesc.persistence.entity.Job;
import ro.hubstudentesc.persistence.repository.JobRepository;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    public Page<JobRecordDto> findAll(Pageable pageable) {
        return jobRepository.findAll(pageable).map(jobMapper::toDto);
    }

    public void addJob(JobRecordDto jobRecordDto){
        jobRepository.save(jobMapper.toEntity(jobRecordDto));
    }

    public void updateJob(Long id , JobRecordDto dto){
        Job job = jobRepository.findById(id).orElseThrow(()->new JobNotFoundException(id));
        job.setTitle(dto.title());
        job.setCity(dto.city());
        job.setCompany(dto.company());
        job.setDescription(dto.description());
        job.setSalary(dto.salary());
        job.setProgram(dto.program());

        jobRepository.save(job);
    }

    public void deleteJob(Long id){
        if (!jobRepository.existsById(id)){
            throw new JobNotFoundException(id);
        }
        jobRepository.deleteById(id);
    }

    public Page<JobRecordDto> findByKeyword(
            String keyword,
            Pageable pageable
    ){
        return jobRepository.searchByKeyword(keyword,pageable).map(jobMapper::toDto);
    }

    public Page<JobRecordDto> findByCity(
            String city,
            Pageable pageable
    ){
        return jobRepository.findByCityIgnoreCase(city, pageable).map(jobMapper::toDto);
    }

    public Page<JobRecordDto> findByProgram(
            String program,
            Pageable pageable
    ){
        return jobRepository.findByProgramIgnoreCase(program,pageable).map(jobMapper::toDto);
    }
}
