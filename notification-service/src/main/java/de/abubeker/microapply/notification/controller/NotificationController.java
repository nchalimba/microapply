package de.abubeker.microapply.notification.controller;

import de.abubeker.microapply.common.dto.ApplicationCreatedDto;
import de.abubeker.microapply.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestBody ApplicationCreatedDto applicationCreatedDto) {
        notificationService.sendNotification(applicationCreatedDto);
        return ResponseEntity.ok().build();
    }
}
