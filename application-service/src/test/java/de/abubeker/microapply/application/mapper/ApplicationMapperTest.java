package de.abubeker.microapply.application.mapper;

import de.abubeker.microapply.application.dto.ApplicationDto;
import de.abubeker.microapply.application.model.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ApplicationMapperTest {
    private ApplicationMapper applicationMapper;

    @BeforeEach
    void setUp() {
        applicationMapper = Mappers.getMapper(ApplicationMapper.class);
    }

    @Test
    void shouldMapApplicationToApplicationDto() {
        // Given
        Application application = Application.builder()
                .id(1L)
                .jobId(2L)
                .userId(3L)
                .status("SUBMITTED")
                .applicationDate(LocalDateTime.now())
                .resumeUrl("resumeUrl")
                .coverLetterUrl("coverLetterUrl")
                .notes("Sample notes")
                .email("email@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .build();

        // When
        ApplicationDto applicationDto = applicationMapper.toDto(application);

        // Then
        assertEquals(application.getId(), applicationDto.id());
        assertEquals(application.getJobId(), applicationDto.jobId());
        assertEquals(application.getUserId(), applicationDto.userId());
        assertEquals(application.getStatus(), applicationDto.status());
        assertEquals(application.getApplicationDate(), applicationDto.applicationDate());
        assertEquals(application.getResumeUrl(), applicationDto.resumeUrl());
        assertEquals(application.getCoverLetterUrl(), applicationDto.coverLetterUrl());
        assertEquals(application.getNotes(), applicationDto.notes());
        assertEquals(application.getEmail(), applicationDto.email());
        assertEquals(application.getFirstName(), applicationDto.firstName());
        assertEquals(application.getLastName(), applicationDto.lastName());
        assertEquals(application.getPhoneNumber(), applicationDto.phoneNumber());
    }

    @Test
    void shouldMapApplicationDtoToApplication() {
        // Given
        ApplicationDto applicationDto = new ApplicationDto(
                1L,
                2L,
                3L,
                "SUBMITTED",
                LocalDateTime.now(),
                "resumeUrl",
                "coverLetterUrl",
                "Sample notes",
                "email@example.com",
                "John",
                "Doe",
                "1234567890"
        );

        // When
        Application application = applicationMapper.toEntity(applicationDto);

        // Then
        assertNull(application.getId()); // because 'id' is ignored in mapping
        assertNull(application.getApplicationDate()); // because 'applicationDate' is ignored in mapping
        assertEquals(applicationDto.jobId(), application.getJobId());
        assertEquals(applicationDto.userId(), application.getUserId());
        assertEquals(applicationDto.status(), application.getStatus());
        assertEquals(applicationDto.resumeUrl(), application.getResumeUrl());
        assertEquals(applicationDto.coverLetterUrl(), application.getCoverLetterUrl());
        assertEquals(applicationDto.notes(), application.getNotes());
        assertEquals(applicationDto.email(), application.getEmail());
        assertEquals(applicationDto.firstName(), application.getFirstName());
        assertEquals(applicationDto.lastName(), application.getLastName());
        assertEquals(applicationDto.phoneNumber(), application.getPhoneNumber());
    }

    @Test
    void shouldUpdateEntityFromDto() {
        // Given
        Application existingApplication = Application.builder()
                .id(1L)
                .jobId(2L)
                .userId(3L)
                .status("SUBMITTED")
                .applicationDate(LocalDateTime.now())
                .resumeUrl("oldResumeUrl")
                .coverLetterUrl("oldCoverLetterUrl")
                .notes("Old notes")
                .email("old_email@example.com")
                .firstName("OldFirstName")
                .lastName("OldLastName")
                .phoneNumber("9876543210")
                .build();

        ApplicationDto applicationDto = new ApplicationDto(
                1L,
                2L,
                3L,
                "UPDATED",
                LocalDateTime.now(),
                "newResumeUrl",
                "newCoverLetterUrl",
                "Updated notes",
                "new_email@example.com",
                "NewFirstName",
                "NewLastName",
                "1234567890"
        );

        // When
        applicationMapper.updateEntityFromDto(applicationDto, existingApplication);

        // Then
        assertEquals(1L, existingApplication.getId()); // ID should remain unchanged
        assertEquals("UPDATED", existingApplication.getStatus());
        assertEquals("newResumeUrl", existingApplication.getResumeUrl());
        assertEquals("newCoverLetterUrl", existingApplication.getCoverLetterUrl());
        assertEquals("Updated notes", existingApplication.getNotes());
        assertEquals("new_email@example.com", existingApplication.getEmail());
        assertEquals("NewFirstName", existingApplication.getFirstName());
        assertEquals("NewLastName", existingApplication.getLastName());
        assertEquals("1234567890", existingApplication.getPhoneNumber());
    }
}
