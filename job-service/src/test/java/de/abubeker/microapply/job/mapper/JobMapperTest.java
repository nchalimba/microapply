package de.abubeker.microapply.job.mapper;

import de.abubeker.microapply.job.dto.JobDto;
import de.abubeker.microapply.job.model.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
public class JobMapperTest {
    private JobMapper jobMapper;

    @BeforeEach
    void setUp() {
        jobMapper = Mappers.getMapper(JobMapper.class);
    }

    @Test
    void shouldMapJobToJobDto() {
        Job job = Job.builder()
                .id(1L)
                .title("Software Engineer")
                .description("Develop software")
                .company("Tech Corp")
                .location("Berlin")
                .status("OPEN")
                .build();

        JobDto jobDto = jobMapper.toDto(job);

        assertEquals(job.getId(), jobDto.id());
        assertEquals(job.getTitle(), jobDto.title());
        assertEquals(job.getDescription(), jobDto.description());
        assertEquals(job.getCompany(), jobDto.company());
        assertEquals(job.getLocation(), jobDto.location());
        assertEquals(job.getStatus(), jobDto.status());
    }

    @Test
    void shouldMapJobDtoToJob() {
        JobDto jobDto = new JobDto(1L, "Software Engineer", "Develop software", "Tech Corp", "Berlin", "OPEN");

        Job job = jobMapper.toEntity(jobDto);

        assertNull(job.getId()); // because 'id' is ignored in mapping
        assertEquals(jobDto.title(), job.getTitle());
        assertEquals(jobDto.description(), job.getDescription());
        assertEquals(jobDto.company(), job.getCompany());
        assertEquals(jobDto.location(), job.getLocation());
        assertEquals(jobDto.status(), job.getStatus());
    }

    @Test
    void shouldUpdateEntityFromDto() {
        Job existingJob = Job.builder()
                .id(1L)
                .title("Old Title")
                .description("Old description")
                .company("Old Company")
                .location("Old Location")
                .status("CLOSED")
                .build();

        JobDto jobDto = new JobDto(1L, "New Title", "New description", "New Company", "New Location", "OPEN");

        jobMapper.updateEntityFromDto(jobDto, existingJob);

        assertEquals(1L, existingJob.getId()); // ID should remain unchanged
        assertEquals(jobDto.title(), existingJob.getTitle());
        assertEquals(jobDto.description(), existingJob.getDescription());
        assertEquals(jobDto.company(), existingJob.getCompany());
        assertEquals(jobDto.location(), existingJob.getLocation());
        assertEquals(jobDto.status(), existingJob.getStatus());
    }
}
