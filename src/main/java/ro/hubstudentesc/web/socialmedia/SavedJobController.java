package ro.hubstudentesc.web.jobs;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.service.jobs.SavedJobService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class SavedJobController {

    private final SavedJobService savedJobService;

    @PostMapping("/{jobId}/save")
    public ResponseEntity<String> saveJob(
            @PathVariable UUID jobId,
            @RequestParam UUID userId) {

        savedJobService.saveJob(userId, jobId);

        return ResponseEntity.ok("Job saved successfully");
    }

    @DeleteMapping("/{jobId}/save")
    public ResponseEntity<String> deleteSavedJob(
            @PathVariable UUID jobId,
            @RequestParam UUID userId) {

        savedJobService.deleteSavedJob(userId, jobId);

        return ResponseEntity.ok("Job removed from saved jobs");
    }
}
