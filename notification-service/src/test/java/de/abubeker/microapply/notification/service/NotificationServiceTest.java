package de.abubeker.microapply.notification.service;

import de.abubeker.microapply.common.dto.ApplicationCreatedDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationServiceTest {
    private static final TestLogger logger = TestLoggerFactory.getTestLogger(NotificationService.class);

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setUpLogger() {
        TestLoggerFactory.clear(); // Clear previous logs
    }

    @Test
    void shouldSendNotificationSuccessfully() throws MessagingException {
        // Given
        ApplicationCreatedDto event = new ApplicationCreatedDto(
                1L,
                LocalDateTime.parse("2024-11-27T17:26:21"),
                "applicant@example.com",
                "John",
                "Doe"
        );

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // When
        notificationService.sendNotification(event);

        // Then
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));

        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(javaMailSender).send(captor.capture());
        MimeMessage capturedMessage = captor.getValue();

        verify(capturedMessage).setRecipient(eq(MimeMessage.RecipientType.TO), any());
        verify(capturedMessage).setSubject(eq("Application Created"));
        verify(capturedMessage).setContent(contains("John Doe"), eq("text/html; charset=utf-8"));
    }


    @Test
    void shouldNotFailWhenEmailIsNull() {
        // Given
        ApplicationCreatedDto event = new ApplicationCreatedDto(
                1L,
                LocalDateTime.parse("2024-11-27T17:26:21"),
                null, // Null email to simulate bad input
                "John",
                "Doe"
        );

        // When
        notificationService.sendNotification(event);

        // Then
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }
}
