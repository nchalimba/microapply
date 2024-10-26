package de.abubeker.microapply.job.service;

import de.abubeker.microapply.common.exception.NotFoundException;
import de.abubeker.microapply.job.dto.JobDto;
import de.abubeker.microapply.job.dto.ValidationResponseDto;
import de.abubeker.microapply.job.mapper.JobMapper;
import de.abubeker.microapply.job.model.Job;
import de.abubeker.microapply.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    public JobDto createJob(JobDto jobRequest) {
        Job job = jobRepository.save(jobMapper.toEntity(jobRequest));
        return jobMapper.toDto(job);
    }

    public JobDto getJob(Long id) {
        return jobRepository
                .findById(id)
                .map(jobMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Job with id " + id + " not found"));
    }

    public List<JobDto> getAllJobs() {
        return jobRepository
                .findAll()
                .stream()
                .map(jobMapper::toDto)
                .toList();
    }

    public void deleteJob(Long id) {
        jobRepository.findById(id)
                .ifPresentOrElse(jobRepository::delete,
                        () -> { throw new NotFoundException("Job with id " + id + " not found"); });
    }

    public JobDto updateJob(Long id, JobDto jobRequest) {
       return jobRepository
               .findById(id)
               .map(job -> {
                   jobMapper.updateEntityFromDto(jobRequest, job);
                   return jobRepository.save(job);
               })
               .map(jobMapper::toDto)
               .orElseThrow(() -> new NotFoundException("Job with id " + id + " not found"));
    }

    public ValidationResponseDto validateJob(Long id) {
        return jobRepository.findById(id)
                .map(job -> new ValidationResponseDto(job.getStatus().equals("OPEN")))
                .orElse(new ValidationResponseDto(false));
    }
}
