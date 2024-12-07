package de.abubeker.microapply.notification;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.SpringApplication;


@TestConfiguration(proxyBeanMethods = false)
public class TestNotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.from(NotificationServiceApplication::main).with(TestNotificationServiceApplication.class).run(args);
    }
}
