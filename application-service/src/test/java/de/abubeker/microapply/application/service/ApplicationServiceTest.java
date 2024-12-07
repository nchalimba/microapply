package de.abubeker.microapply.application.service;

import de.abubeker.microapply.application.client.JobClient;
import de.abubeker.microapply.application.config.RabbitMQConfig;
import de.abubeker.microapply.application.dto.ApplicationDto;
import de.abubeker.microapply.application.dto.JobValidationResponseDto;
import de.abubeker.microapply.application.mapper.ApplicationMapper;
import de.abubeker.microapply.application.model.Application;
import de.abubeker.microapply.application.repository.ApplicationRepository;
import de.abubeker.microapply.common.dto.ApplicationCreatedDto;
import de.abubeker.microapply.common.exception.BadRequestException;
import de.abubeker.microapply.common.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationMapper applicationMapper;

    @Mock
    private JobClient jobClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createApplication_ValidJob_CreatesApplicationAndSendsEvent() {
        // Given
        ApplicationDto applicationRequest = new ApplicationDto(1L, 2L, 3L, "status", null, "resumeUrl", "coverLetterUrl", "notes", "john.due@example.com", "John", "Doe", "123456789");
        Application application = new Application();

        when(jobClient.isJobAvailable(applicationRequest.jobId())).thenReturn(new JobValidationResponseDto(true));
        when(applicationMapper.toEntity(applicationRequest)).thenReturn(application);
        when(applicationRepository.save(any())).thenReturn(application);
        when(applicationMapper.toDto(application)).thenReturn(applicationRequest);

        // When
        ApplicationDto result = applicationService.createApplication(applicationRequest);

        // Then
        assertNotNull(result);
        assertEquals(applicationRequest.jobId(), result.jobId());
        verify(rabbitTemplate).convertAndSend(eq(RabbitMQConfig.EXCHANGE_NAME), eq(RabbitMQConfig.ROUTING_KEY), any(ApplicationCreatedDto.class));
    }

    @Test
    void createApplication_InvalidJob_ThrowsBadRequestException() {
        // Given
        ApplicationDto applicationRequest = new ApplicationDto(1L, 2L, 3L, "status", null, "resumeUrl", "coverLetterUrl","notes", "email@example.com", "John", "Doe", "123456789");
        when(jobClient.isJobAvailable(applicationRequest.jobId())).thenReturn(new JobValidationResponseDto(false));

        // When
        BadRequestException exception = assertThrows(BadRequestException.class, () -> applicationService.createApplication(applicationRequest));

        // Then
        assertEquals("Job with id 2 is not available", exception.getMessage());
        verifyNoInteractions(applicationRepository, rabbitTemplate);
    }

    @Test
    void getApplication_ExistingId_ReturnsApplicationDto() {
        // Given
        Long id = 1L;
        Application application = new Application();
        ApplicationDto applicationDto = new ApplicationDto(id, 2L, 3L, "status", null, "resumeUrl", "coverLetterUrl", "notes", "email@example.com", "John", "Doe", "123456789");

        when(applicationRepository.findById(id)).thenReturn(Optional.of(application));
        when(applicationMapper.toDto(application)).thenReturn(applicationDto);

        // When
        ApplicationDto result = applicationService.getApplication(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.id());
    }

    @Test
    void getApplication_NonExistingId_ThrowsNotFoundException() {
        // Given
        Long id = 1L;
        when(applicationRepository.findById(id)).thenReturn(Optional.empty());

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> applicationService.getApplication(id));
        // Then
        assertEquals("Application with id 1 not found", exception.getMessage());
    }

    @Test
    void getAllApplications_ReturnsAllApplications() {
        // Given
        List<Application> applications = List.of(new Application(), new Application());
        List<ApplicationDto> applicationDtos = List.of(new ApplicationDto(1L, 2L, 3L, "status", null, "resumeUrl", "coverLetterUrl", "notes", "email@example.com", "John", "Doe", "123456789"));

        when(applicationRepository.findAll()).thenReturn(applications);
        when(applicationMapper.toDto(any(Application.class))).thenReturn(applicationDtos.get(0));

        // When
        List<ApplicationDto> result = applicationService.getAllApplications();

        // Then
        assertEquals(2, result.size());
        verify(applicationRepository).findAll();
    }

    @Test
    void updateApplication_ExistingId_UpdatesAndReturnsApplication() {
        // Given
        Long id = 1L;
        Application application = new Application();
        ApplicationDto applicationRequest = new ApplicationDto(id, 2L, 3L, "status", null, "resumeUrl", "coverLetterUrl", "notes", "email@example.com", "John", "Doe", "123456789");

        when(applicationRepository.findById(id)).thenReturn(Optional.of(application));
        when(applicationRepository.save(application)).thenReturn(application);
        when(applicationMapper.toDto(application)).thenReturn(applicationRequest);

        // When
        ApplicationDto result = applicationService.updateApplication(id, applicationRequest);

        // Then
        assertNotNull(result);
        assertEquals(applicationRequest, result);
    }

    @Test
    void deleteApplication_ExistingId_DeletesApplication() {
        // Given
        Long id = 1L;
        Application application = new Application();

        when(applicationRepository.findById(id)).thenReturn(Optional.of(application));

        // When
        applicationService.deleteApplication(id);

        // Then
        verify(applicationRepository).delete(application);
    }

    @Test
    void deleteApplication_NonExistingId_ThrowsNotFoundException() {
        // Given
        Long id = 1L;
        when(applicationRepository.findById(id)).thenReturn(Optional.empty());

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> applicationService.deleteApplication(id));
        // Then
        assertEquals("Application with id 1 not found", exception.getMessage());
    }
}
