package de.abubeker.microapply.notification.service;

import de.abubeker.microapply.common.dto.ApplicationCreatedDto;
import de.abubeker.microapply.notification.config.RabbitMQConfig;
import de.abubeker.microapply.notification.constants.EmailConstants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = RabbitMQConfig.APPLICATION_CREATED_QUEUE)
    public void sendNotification(ApplicationCreatedDto event) {
        try {
            log.info("Sending notification, event: {}", event.toString());
            System.out.println(LocalDateTime.now());
            sendEmail(event);
        } catch (Exception e) {
            log.error("Failed to send notification", e);
        }
    }

    private void sendEmail(ApplicationCreatedDto event) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(event.email()));
        message.setSubject("Application Created");
        message.setContent(EmailConstants.APPLICATION_CREATED_EMAIL_TEMPLATE.formatted(event.firstName() + " " + event.lastName()), "text/html; charset=utf-8");
        javaMailSender.send(message);
        log.info("Email sent to {}", event.email());
    }
}
