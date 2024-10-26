package de.abubeker.microapply.job.controller;

import de.abubeker.microapply.job.dto.JobDto;
import de.abubeker.microapply.job.dto.ValidationResponseDto;
import de.abubeker.microapply.job.service.JobService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class JobControllerTest {

    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    private MockMvc mockMvc;

    private JobDto jobDto;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();

        jobDto = new JobDto(
                1L,
                "Software Developer",
                "Develop software",
                "Tech Corp",
                "Berlin",
                "OPEN"
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testCreateJob() throws Exception {
        when(jobService.createJob(any(JobDto.class))).thenReturn(jobDto);

        mockMvc.perform(post("/api/job")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Software Developer\", \"description\": \"Develop software\", \"company\": \"Tech Corp\", \"location\": \"Berlin\", \"status\": \"OPEN\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Software Developer")));
    }

    @Test
    void testGetAllJobs() throws Exception {
        when(jobService.getAllJobs()).thenReturn(List.of(jobDto));

        mockMvc.perform(get("/api/job"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Software Developer")));
    }

    @Test
    void testGetJob() throws Exception {
        when(jobService.getJob(1L)).thenReturn(jobDto);

        mockMvc.perform(get("/api/job/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Software Developer")));
    }

    @Test
    void testUpdateJob() throws Exception {
        when(jobService.updateJob(eq(1L), any(JobDto.class))).thenReturn(jobDto);

        mockMvc.perform(put("/api/job/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Software Developer\", \"description\": \"Develop software\", \"company\": \"Tech Corp\", \"location\": \"Berlin\", \"status\": \"OPEN\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Software Developer")));
    }

    @Test
    void testDeleteJob() throws Exception {
        doNothing().when(jobService).deleteJob(1L);

        mockMvc.perform(delete("/api/job/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testValidateJob() throws Exception {
        when(jobService.validateJob(1L)).thenReturn(new ValidationResponseDto(true));

        mockMvc.perform(get("/api/job/1/validate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid", is(true)));
    }
}
