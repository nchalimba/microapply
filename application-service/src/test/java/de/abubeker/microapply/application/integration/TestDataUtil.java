package de.abubeker.microapply.application.integration;

import de.abubeker.microapply.application.model.Application;
import de.abubeker.microapply.application.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TestDataUtil {

    @Autowired
    private ApplicationRepository applicationRepository;

    /**
     * Deletes all application data from the repository.
     */
    public void deleteApplicationData() {
        applicationRepository.deleteAll();
    }

    /**
     * Inserts sample application data into the repository.
     *
     * @return List of saved Application entities.
     */
    public List<Application> insertApplicationData() {
        return applicationRepository.saveAll(List.of(
                Application.builder()
                        .id(1L)
                        .jobId(101L)
                        .userId(201L)
                        .status("SUBMITTED")
                        .applicationDate(LocalDateTime.now().minusDays(2))
                        .resumeUrl("http://example.com/resume1.pdf")
                        .coverLetterUrl("http://example.com/cover1.pdf")
                        .notes("Candidate is very experienced.")
                        .email("candidate1@example.com")
                        .firstName("John")
                        .lastName("Doe")
                        .phoneNumber("1234567890")
                        .build(),
                Application.builder()
                        .id(2L)
                        .jobId(102L)
                        .userId(202L)
                        .status("REVIEWED")
                        .applicationDate(LocalDateTime.now().minusDays(5))
                        .resumeUrl("http://example.com/resume2.pdf")
                        .coverLetterUrl("http://example.com/cover2.pdf")
                        .notes("Candidate has a strong portfolio.")
                        .email("candidate2@example.com")
                        .firstName("Jane")
                        .lastName("Smith")
                        .phoneNumber("0987654321")
                        .build()
        ));
    }
}
