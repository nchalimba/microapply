package de.abubeker.microapply.job;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestJobServiceApplication {
    @Bean
    @ServiceConnection
    MySQLContainer<?> mySQLContainer() {
        return new MySQLContainer<>(DockerImageName.parse("mysql:8.4.2"));
    }

    public static void main(String[] args) {
        SpringApplication.from(JobServiceApplication::main).with(TestJobServiceApplication.class).run(args);
    }
}
