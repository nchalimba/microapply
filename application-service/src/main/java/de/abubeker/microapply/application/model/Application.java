package de.abubeker.microapply.application.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long jobId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String status;
    private LocalDateTime applicationDate;
    private String resumeUrl;
    private String coverLetterUrl;
    private String notes;
    private String internalNotes; // comments made by recruiters
}
