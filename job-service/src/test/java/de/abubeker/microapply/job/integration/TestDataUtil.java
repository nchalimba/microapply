package de.abubeker.microapply.job.integration;

import de.abubeker.microapply.job.model.Job;
import de.abubeker.microapply.job.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestDataUtil {

    @Autowired
    private JobRepository jobRepository;

    public void deleteJobData() {
        jobRepository.deleteAll();
    }

    public List<Job> insertJobData() {
        return jobRepository.saveAll(List.of(
                new Job(1L, "Developer", "Backend Developer", "Tech Corp", "Berlin", "OPEN"),
                new Job(2L, "Analyst", "Data Analyst", "Analytics Ltd", "Munich", "OPEN")
        ));
    }
}