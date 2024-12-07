package de.abubeker.microapply.notification.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.abubeker.microapply.common.dto.ApplicationCreatedDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestDataUtil {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public TestDataUtil(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendTestMessage(String queueName, ApplicationCreatedDto dto) {
        rabbitTemplate.convertAndSend(queueName, dto);
    }
}
