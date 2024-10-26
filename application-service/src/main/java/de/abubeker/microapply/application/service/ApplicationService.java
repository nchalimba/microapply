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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final JobClient jobClient;
    private final RabbitTemplate rabbitTemplate;

    public ApplicationDto createApplication(ApplicationDto applicationRequest) {
        JobValidationResponseDto response = jobClient.isJobAvailable(applicationRequest.jobId());
        if (!response.isValid()) {
            //TODO: maybe create custom exception
            throw new BadRequestException("Job with id " + applicationRequest.jobId() + " is not available");
        }

        Application application = applicationMapper.toEntity(applicationRequest);
        application.setApplicationDate(LocalDateTime.now());
        ApplicationDto applicationDto = applicationMapper.toDto(applicationRepository.save(application));

        sendApplicationCreatedEvent(applicationDto);
        return applicationDto;
    }

    public ApplicationDto getApplication(Long id) {
        return applicationRepository
                .findById(id)
                .map(applicationMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Application with id " + id + " not found"));
    }

    public List<ApplicationDto> getAllApplications() {
        return applicationRepository
                .findAll()
                .stream()
                .map(applicationMapper::toDto)
                .toList();
    }

    public ApplicationDto updateApplication(Long id, ApplicationDto applicationRequest) {
        return applicationRepository
                .findById(id)
                .map(application -> {
                    applicationMapper.updateEntityFromDto(applicationRequest, application);
                    return applicationRepository.save(application);
                })
                .map(applicationMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Application with id " + id + " not found"));
    }

    public void deleteApplication(Long id) {
        applicationRepository
                .findById(id)
                .ifPresentOrElse(applicationRepository::delete,
                        () -> { throw new NotFoundException("Application with id " + id + " not found"); });
    }

    private void sendApplicationCreatedEvent(ApplicationDto applicationDto) {
        ApplicationCreatedDto message = new ApplicationCreatedDto(
                applicationDto.id(),
                applicationDto.applicationDate(),
                applicationDto.email()
        );

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
        log.info("Sent application created event for job ID {}", message.applicationId());
    }
}
