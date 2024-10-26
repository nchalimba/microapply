package de.abubeker.microapply.application.client;

import de.abubeker.microapply.application.dto.JobValidationResponseDto;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Slf4j
public interface JobClient {
    Logger log = LoggerFactory.getLogger(JobClient.class);

    @GetExchange("/api/job/{id}/validate")
    @CircuitBreaker(name = "job", fallbackMethod = "fallbackMethod")
    @Retry(name = "job")
    JobValidationResponseDto isJobAvailable(@PathVariable Long id);

    default boolean fallbackMethod(Long id, Throwable throwable) {
        log.info("Cannot get job with id {}, failure reason: {}", id, throwable.getMessage());
        return false;
    }
}
