package de.abubeker.microapply.job.repository;

import de.abubeker.microapply.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
