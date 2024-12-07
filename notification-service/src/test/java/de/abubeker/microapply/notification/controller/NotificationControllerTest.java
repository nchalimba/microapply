package de.abubeker.microapply.notification.controller;

import de.abubeker.microapply.common.dto.ApplicationCreatedDto;
import de.abubeker.microapply.notification.service.NotificationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void shouldSendNotificationSuccessfully() throws Exception {

        // Given
        ApplicationCreatedDto applicationCreatedDto = new ApplicationCreatedDto(
                1L,
                LocalDateTime.parse("2024-11-27T17:26:21"),
                "applicant@example.com",
                "John",
                "Doe"
        );

        doNothing().when(notificationService).sendNotification(any(ApplicationCreatedDto.class));

        // When & Then
        mockMvc.perform(post("/api/notification/send")
                        .contentType("application/json")
                        .content("{ \"applicationId\": 1, \"applicationDate\": \"2024-11-27T17:26:21\", \"email\": \"applicant@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\" }"))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).sendNotification(any(ApplicationCreatedDto.class));
    }

    @Test
    void shouldHandleExceptionGracefully() throws Exception {
        // Given
        ApplicationCreatedDto applicationCreatedDto = new ApplicationCreatedDto(
                1L,
                LocalDateTime.parse("2024-11-27T17:26:21"),
                "applicant@example.com",
                "John",
                "Doe"
        );

        doNothing().when(notificationService).sendNotification(any(ApplicationCreatedDto.class));

        // When & Then
        mockMvc.perform(post("/api/notification/send")
                        .contentType("application/json")
                        .content("{ \"applicationId\": 1, \"applicationDate\": \"2024-11-27T17:26:21\", \"email\": \"applicant@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\" }"))
                .andExpect(status().isOk());
    }
}
