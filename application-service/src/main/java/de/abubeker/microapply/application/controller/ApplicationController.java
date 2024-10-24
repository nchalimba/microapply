package de.abubeker.microapply.application.controller;

import de.abubeker.microapply.application.dto.ApplicationDto;
import de.abubeker.microapply.application.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationDto> createApplication(@Valid @RequestBody ApplicationDto applicationRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationService.createApplication(applicationRequest));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplication(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplication(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDto> updateApplication(@PathVariable Long id, @Valid @RequestBody ApplicationDto applicationRequest) {
        return ResponseEntity.ok(applicationService.updateApplication(id, applicationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
