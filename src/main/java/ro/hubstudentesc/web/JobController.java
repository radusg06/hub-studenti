package ro.hubstudentesc.web;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.JobRecordDto;
import ro.hubstudentesc.service.JobService;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<Page<JobRecordDto>> getJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String program,
            Pageable pageable){
        if (keyword != null && !keyword.isBlank()) {
            return ResponseEntity.ok(jobService.findByKeyword(keyword,pageable));
        }

        if(city != null && !city.isBlank()){
            return ResponseEntity.ok(jobService.findByCity(city,pageable));
        }

        if(program !=null && !program.isBlank()){
            return ResponseEntity.ok(jobService.findByProgram(program,pageable));
        }

        return ResponseEntity.ok(jobService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<String> createJob(
            @RequestBody
            @Valid
            JobRecordDto jobRecordDto){
        jobService.addJob(jobRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Job created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(
            @PathVariable Long id,
            @RequestBody @Valid JobRecordDto jobRecordDto) {

                jobService.updateJob(id , jobRecordDto);
                return ResponseEntity.ok("Job updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {

        jobService.deleteJob(id);

        return ResponseEntity.ok("Job deleted successfully");
    }



}
