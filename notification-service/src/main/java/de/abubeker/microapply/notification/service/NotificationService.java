package de.abubeker.microapply.notification.service;

import de.abubeker.microapply.common.dto.ApplicationCreatedDto;
import de.abubeker.microapply.notification.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    @RabbitListener(queues = RabbitMQConfig.APPLICATION_CREATED_QUEUE)
    public void sendNotification(ApplicationCreatedDto event) {
        try {
            log.info("Sending notification, event: {}", event.toString());
        } catch (Exception e) {
            log.error("Failed to send notification", e);
        }
    }
}
