package ro.hubstudentesc.web.jobs;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.common.PageEnvelopeDto;
import ro.hubstudentesc.dto.common.PaginationDto;
import ro.hubstudentesc.dto.jobs.JobApplicationCreateDto;
import ro.hubstudentesc.dto.jobs.JobApplicationResponseDto;
import ro.hubstudentesc.dto.jobs.JobApplicationStatusDto;
import ro.hubstudentesc.dto.jobs.JobCreateDto;
import ro.hubstudentesc.dto.jobs.JobFeedItemDto;
import ro.hubstudentesc.dto.jobs.JobResponseDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.service.jobs.JobApplicationService;
import ro.hubstudentesc.service.jobs.JobService;

import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobApplicationService jobApplicationService;
    private final JobService jobService;

    /**
     * Lista joburilor este publica.
     *
     * GET /api/v1/jobs
     */
    @GetMapping
    public ResponseEntity<PageEnvelopeDto<JobFeedItemDto>> getJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String skills,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        int normalizedLimit = Math.max(1, Math.min(limit, 100));
        Page<JobFeedItemDto> jobs = jobService.findJobs(
                location,
                type,
                parseSkills(skills),
                PageRequest.of(Math.max(page, 1) - 1, normalizedLimit)
        );

        return ResponseEntity.ok(new PageEnvelopeDto<>(
                jobs.getContent(),
                new PaginationDto(
                        jobs.getNumber() + 1,
                        jobs.getTotalPages(),
                        jobs.getTotalElements()
                )
        ));
    }

    private String[] parseSkills(String skills) {
        if (skills == null || skills.isBlank()) {
            return null;
        }

        return Arrays.stream(skills.split(","))
                .map(String::trim)
                .filter(skill -> !skill.isBlank())
                .toArray(String[]::new);
    }

    /**
     * Studentul autentificat aplica la un job.
     *
     * POST /api/v1/jobs/{jobId}/apply
     */
    @PostMapping("/{jobId}/apply")
    public ResponseEntity<JobApplicationResponseDto> createApplication(
            Authentication authentication,
            @PathVariable UUID jobId,
            @RequestBody @Valid JobApplicationCreateDto dto
    ) {

        User user = (User) authentication.getPrincipal();

        JobApplicationResponseDto response =
                jobApplicationService.addApplication(
                        jobId,
                        user.getId(),
                        dto
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Compania autentificata publica un job.
     *
     * POST /api/v1/jobs
     */
    @PostMapping
    public ResponseEntity<JobResponseDto> createJob(
            Authentication authentication,
            @RequestBody @Valid JobCreateDto dto
    ) {

        User user = (User) authentication.getPrincipal();

        JobResponseDto response =
                jobService.addJob(
                        user.getId(),
                        dto
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Compania autentificata isi modifica propriul job.
     *
     * PUT /api/v1/jobs/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDto> updateJob(
            Authentication authentication,
            @PathVariable UUID id,
            @RequestBody @Valid JobCreateDto dto
    ) {

        User user = (User) authentication.getPrincipal();

        JobResponseDto response =
                jobService.updateJob(
                        id,
                        user.getId(),
                        dto
                );

        return ResponseEntity.ok(response);
    }

    /**
     * Compania autentificata isi sterge propriul job.
     *
     * DELETE /api/v1/jobs/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(
            Authentication authentication,
            @PathVariable UUID id
    ) {

        User user = (User) authentication.getPrincipal();

        jobService.deleteJob(
                id,
                user.getId()
        );

        return ResponseEntity.ok(
                "Job deleted successfully"
        );
    }

    /**
     * Compania autentificata modifica statusul unei candidaturi
     * pentru unul dintre propriile joburi.
     *
     * PATCH /api/v1/jobs/applications/{id}/status
     */
    @PatchMapping("/applications/{id}/status")
    public ResponseEntity<JobApplicationResponseDto> updateApplicationStatus(
            Authentication authentication,
            @PathVariable UUID id,
            @RequestBody @Valid JobApplicationStatusDto dto
    ) {

        User user = (User) authentication.getPrincipal();

        JobApplicationResponseDto response =
                jobApplicationService.updateStatus(
                        id,
                        user.getId(),
                        dto
                );

        return ResponseEntity.ok(response);
    }
}
