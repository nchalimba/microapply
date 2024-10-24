package de.abubeker.microapply.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ApplicationDto(
        Long id,
        @NotNull(message = "Job cannot be null")
        Long jobId,
        @NotNull(message = "User cannot be null")
        Long userId,
        @NotBlank(message = "Status cannot be empty")
        String status,
        LocalDateTime applicationDate,
        String resumeUrl,
        String coverLetterUrl,
        String notes) {
}
