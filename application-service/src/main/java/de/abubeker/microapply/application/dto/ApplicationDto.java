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
        @NotBlank(message = "Resume URL cannot be empty")
        String resumeUrl,
        String coverLetterUrl,
        String notes,
        @NotBlank(message = "Email cannot be empty")
        String email,
        @NotBlank(message = "First name cannot be empty")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        String lastName,
        @NotBlank(message = "Phone number cannot be empty")
        String phoneNumber
) {
}
