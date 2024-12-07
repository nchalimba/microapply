package de.abubeker.microapply.application.controller;

import de.abubeker.microapply.application.dto.ApplicationDto;
import de.abubeker.microapply.application.service.ApplicationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ApplicationControllerTest {

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationController applicationController;

    private MockMvc mockMvc;

    private ApplicationDto applicationDto;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationController).build();

        applicationDto = new ApplicationDto(
                1L,
                1L,
                1L,
                "SUBMITTED",
                LocalDateTime.now(),
                "resumeUrl",
                "coverLetterUrl",
                "notes",
                "email@example.com",
                "John",
                "Doe",
                "1234567890"
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testCreateApplication() throws Exception {
        when(applicationService.createApplication(any(ApplicationDto.class))).thenReturn(applicationDto);

        mockMvc.perform(post("/api/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "jobId": 1,
                                  "userId": 1,
                                  "status": "SUBMITTED",
                                  "resumeUrl": "resumeUrl",
                                  "coverLetterUrl": "coverLetterUrl",
                                  "email": "email@example.com",
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "phoneNumber": "1234567890"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("email@example.com")));
    }

    @Test
    void testGetAllApplications() throws Exception {
        when(applicationService.getAllApplications()).thenReturn(List.of(applicationDto));

        mockMvc.perform(get("/api/application"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].email", is("email@example.com")));
    }

    @Test
    void testGetApplication() throws Exception {
        when(applicationService.getApplication(1L)).thenReturn(applicationDto);

        mockMvc.perform(get("/api/application/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("email@example.com")));
    }

    @Test
    void testUpdateApplication() throws Exception {
        when(applicationService.updateApplication(eq(1L), any(ApplicationDto.class))).thenReturn(applicationDto);

        mockMvc.perform(put("/api/application/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "jobId": 1,
                                  "userId": 1,
                                  "status": "UPDATED",
                                  "resumeUrl": "resumeUrl",
                                  "coverLetterUrl": "coverLetterUrl",
                                  "email": "updated@example.com",
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "phoneNumber": "1234567890"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("SUBMITTED")));
    }

    @Test
    void testDeleteApplication() throws Exception {
        doNothing().when(applicationService).deleteApplication(1L);

        mockMvc.perform(delete("/api/application/1"))
                .andExpect(status().isNoContent());
    }
}
