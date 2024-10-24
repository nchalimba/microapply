package de.abubeker.microapply.job.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record JobDto(
        Long id,
        @NotBlank(message = "Title cannot be empty") String title,
        String description,
        @NotBlank(message = "Company cannot be empty") String company,
        String location,
        // status should be either "OPEN" or "CLOSED"
        @NotBlank(message = "Status cannot be empty")
        @Pattern(regexp = "^(OPEN|CLOSED)$", message = "Status must be either OPEN or CLOSED")
        String status
) {
}
