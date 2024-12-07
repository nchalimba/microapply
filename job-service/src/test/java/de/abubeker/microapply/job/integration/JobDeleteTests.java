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
        testDataUtil.deleteJobData();
        jobIds = testDataUtil.insertJobData().stream().map(Job::getId).toList();

    }

    @Test
    void shouldDeleteJobById() {
        RestAssured
                .when()
                .get("/api/job/" + jobIds.getFirst()) // Assuming job with ID 1 exists
                .then()
                .statusCode(200);

        RestAssured
                .when()
                .delete("/api/job/" + jobIds.getFirst()) // Deleting job with ID 1
                .then()
                .statusCode(204);

        RestAssured
                .when()
                .get("/api/job/" + jobIds.getFirst()) // Trying to get the deleted job
                .then()
                .statusCode(404)
                .body("message", containsString("Job with id " + jobIds.getFirst() + " not found"));
    }

    @Test
    void shouldReturn404ForNonExistentJobIdOnDelete() {
        RestAssured
                .when()
                .delete("/api/job/9999") // Assuming 9999 does not exist
                .then()
                .statusCode(404)
                .body("message", containsString("Job with id 9999 not found"));
    }
}
