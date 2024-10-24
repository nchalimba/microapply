package de.abubeker.microapply.job.integration;

import de.abubeker.microapply.job.model.Job;
import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class JobDeleteTests extends BaseIntegrationTest{
    @Autowired
    private TestDataUtil testDataUtil;

    private List<Long> jobIds;

    @BeforeEach
    void setup() {
        super.setup();
        testDataUtil.deleteJobData(); // Clean up existing job data
        jobIds = testDataUtil.insertJobData().stream().map(Job::getId).toList();

    }

    @Test
    void shouldDeleteJobById() {
        // First, ensure that the job exists before deletion
        RestAssured
                .when()
                .get("/api/job/" + jobIds.getFirst()) // Assuming job with ID 1 exists
                .then()
                .statusCode(200);

        // Now, delete the job
        RestAssured
                .when()
                .delete("/api/job/" + jobIds.getFirst()) // Deleting job with ID 1
                .then()
                .statusCode(204); // Assert that the response status is 204 No Content

        // Verify that the job is deleted by trying to retrieve it
        RestAssured
                .when()
                .get("/api/job/" + jobIds.getFirst()) // Trying to get the deleted job
                .then()
                .statusCode(404) // Assert that the response status is 404
                .body("message", containsString("Job with id " + jobIds.getFirst() + " not found")); // Assert error message
    }

    @Test
    void shouldReturn404ForNonExistentJobIdOnDelete() {
        // Attempt to delete a non-existent job
        RestAssured
                .when()
                .delete("/api/job/9999") // Assuming 9999 does not exist
                .then()
                .statusCode(404) // Assert that the response status is 404
                .body("message", containsString("Job with id 9999 not found")); // Assert error message
    }
}
