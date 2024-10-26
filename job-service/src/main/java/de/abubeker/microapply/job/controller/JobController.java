package de.abubeker.microapply.job.controller;

import de.abubeker.microapply.job.dto.JobDto;
import de.abubeker.microapply.job.dto.ValidationResponseDto;
import de.abubeker.microapply.job.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobDto> createJob(@Valid @RequestBody JobDto jobRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jobService.createJob(jobRequest));
    }

    @GetMapping
    public ResponseEntity<List<JobDto>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> getJob(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJob(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDto> updateJob(@PathVariable Long id, @Valid @RequestBody JobDto jobRequest) {
        return ResponseEntity.ok(jobService.updateJob(id, jobRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/validate")
    public ResponseEntity<ValidationResponseDto> validateJob(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.validateJob(id));
    }
}
