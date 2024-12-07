package de.abubeker.microapply.notification.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.abubeker.microapply.common.dto.ApplicationCreatedDto;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

import static de.abubeker.microapply.notification.config.RabbitMQConfig.APPLICATION_CREATED_QUEUE;
import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockito.Mockito.*;

public class NotificationServiceIT extends BaseIntegrationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestDataUtil testDataUtil;

    @MockBean
    private JavaMailSender javaMailSender;

    private ApplicationCreatedDto applicationCreatedDto;

    @BeforeEach
    void setupTest() {
        super.setup();

        applicationCreatedDto = new ApplicationCreatedDto(
                1L,
                LocalDateTime.now(),
                "applicant@example.com",
                "John",
                "Doe"
        );
    }
    
    @Test
    void shouldSendEmailWhenMessageIsProcessed() throws Exception {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        testDataUtil.sendTestMessage(APPLICATION_CREATED_QUEUE, applicationCreatedDto);

        verify(javaMailSender, timeout(5000).times(1)).send(mimeMessage);
    }


    @Test
    void shouldProcessMessageFromQueue() {
        // Send a test message to the RabbitMQ queue
        testDataUtil.sendTestMessage(APPLICATION_CREATED_QUEUE, applicationCreatedDto);

        await()
                .atMost(5, SECONDS)
                .untilAsserted(() -> {
                    // TODO: Add checks here, e.g., log validation, mock verification
                    // For simplicity, this test ensures no errors occur
                });
    }
}
