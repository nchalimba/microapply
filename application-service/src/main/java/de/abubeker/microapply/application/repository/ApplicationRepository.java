package de.abubeker.microapply.application.repository;

import de.abubeker.microapply.application.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
