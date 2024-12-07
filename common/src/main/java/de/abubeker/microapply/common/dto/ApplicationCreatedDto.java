package de.abubeker.microapply.common.dto;

import java.time.LocalDateTime;

public record ApplicationCreatedDto(
        Long applicationId,
        LocalDateTime timestamp,
        String email,
        String firstName,
        String lastName
) {
}
