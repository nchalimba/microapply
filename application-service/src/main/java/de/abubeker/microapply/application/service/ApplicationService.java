package de.abubeker.microapply.application.service;

import de.abubeker.microapply.application.dto.ApplicationDto;
import de.abubeker.microapply.application.mapper.ApplicationMapper;
import de.abubeker.microapply.application.model.Application;
import de.abubeker.microapply.application.repository.ApplicationRepository;
import de.abubeker.microapply.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public ApplicationDto createApplication(ApplicationDto applicationRequest) {
        Application application = applicationMapper.toEntity(applicationRequest);
        application.setApplicationDate(LocalDateTime.now());
        return applicationMapper.toDto(applicationRepository.save(application));
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
}
