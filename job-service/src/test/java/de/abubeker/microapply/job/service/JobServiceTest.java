package de.abubeker.microapply.job.service;

import de.abubeker.microapply.common.exception.NotFoundException;
import de.abubeker.microapply.job.dto.JobDto;
import de.abubeker.microapply.job.dto.ValidationResponseDto;
import de.abubeker.microapply.job.mapper.JobMapper;
import de.abubeker.microapply.job.model.Job;
import de.abubeker.microapply.job.repository.JobRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JobServiceTest {
    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobMapper jobMapper;

    @InjectMocks
    private JobService jobService;

    private Job job;
    private JobDto jobDto;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        job = new Job(1L, "Software Developer", "Develop software", "Tech Corp", "Berlin", "OPEN");
        jobDto = new JobDto(1L, "Software Developer", "Develop software", "Tech Corp", "Berlin", "OPEN");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void createJob_shouldReturnCreatedJob() {
        when(jobMapper.toEntity(any(JobDto.class))).thenReturn(job);
        when(jobRepository.save(any(Job.class))).thenReturn(job);
        when(jobMapper.toDto(any(Job.class))).thenReturn(jobDto);

        JobDto createdJob = jobService.createJob(jobDto);

        assertNotNull(createdJob);
        assertEquals(jobDto.id(), createdJob.id());
        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    void getJob_shouldReturnJobWhenFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobMapper.toDto(any(Job.class))).thenReturn(jobDto);

        JobDto foundJob = jobService.getJob(1L);

        assertNotNull(foundJob);
        assertEquals(jobDto.id(), foundJob.id());
        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void getJob_shouldThrowNotFoundExceptionWhenNotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> jobService.getJob(1L));
        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void getAllJobs_shouldReturnListOfJobs() {
        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(jobMapper.toDto(any(Job.class))).thenReturn(jobDto);

        List<JobDto> allJobs = jobService.getAllJobs();

        assertEquals(1, allJobs.size());
        verify(jobRepository, times(1)).findAll();
    }

    @Test
    void deleteJob_shouldDeleteJobWhenFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        jobService.deleteJob(1L);

        verify(jobRepository, times(1)).delete(job);
    }

    @Test
    void deleteJob_shouldThrowNotFoundExceptionWhenNotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> jobService.deleteJob(1L));
        verify(jobRepository, never()).delete(any(Job.class));
    }

    @Test
    void updateJob_shouldUpdateJobWhenFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobMapper.toDto(any(Job.class))).thenReturn(jobDto);
        when(jobRepository.save(any(Job.class))).thenReturn(job);

        JobDto updatedJob = jobService.updateJob(1L, jobDto);

        assertNotNull(updatedJob);
        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    void updateJob_shouldThrowNotFoundExceptionWhenNotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> jobService.updateJob(1L, jobDto));
        verify(jobRepository, never()).save(any(Job.class));
    }

    @Test
    void validateJob_shouldReturnTrueWhenJobIsOpen() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        ValidationResponseDto response = jobService.validateJob(1L);

        assertTrue(response.isValid());
        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void validateJob_shouldReturnFalseWhenJobIsClosed() {
        job.setStatus("CLOSED");
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        ValidationResponseDto response = jobService.validateJob(1L);

        assertFalse(response.isValid());
        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void validateJob_shouldReturnFalseWhenJobNotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        ValidationResponseDto response = jobService.validateJob(1L);

        assertFalse(response.isValid());
        verify(jobRepository, times(1)).findById(1L);
    }
}