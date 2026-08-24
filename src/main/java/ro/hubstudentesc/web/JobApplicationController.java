package ro.hubstudentesc.web;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.JobApplicationRecordDto;
import ro.hubstudentesc.dto.JobApplicationStatusDto;
import ro.hubstudentesc.service.JobApplicationService;


@RestController
@RequestMapping("/api/job-applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @GetMapping
    public ResponseEntity<Page<JobApplicationRecordDto>> getApplications(Pageable pageable) {
        return ResponseEntity.ok(jobApplicationService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<String> createApplication(
            @RequestBody @Valid JobApplicationRecordDto dto
    ){
        jobApplicationService.addApplication(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Application creaeted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateApplication(
            @PathVariable Long id,
            @RequestBody @Valid JobApplicationRecordDto dto
    ){
        jobApplicationService.updateApplication(id, dto);
        return ResponseEntity.ok("Application updated successfully");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestBody JobApplicationStatusDto dto
            ){
        jobApplicationService.updateStatus(id,dto);
        return ResponseEntity.ok("Application status updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(
            @PathVariable Long id
    ){
        jobApplicationService.deleteApplication(id);
        return ResponseEntity.ok("Application deleted successfully");
    }
}
