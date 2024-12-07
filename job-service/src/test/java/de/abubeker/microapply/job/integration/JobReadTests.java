package de.abubeker.microapply.job.integration;

import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class JobReadTests extends BaseIntegrationTest{
    @Autowired
    private TestDataUtil testDataUtil;
    private Long firstId;

    @BeforeEach
    void setup() {
        super.setup();
        testDataUtil.deleteJobData();
        firstId = testDataUtil.insertJobData().getFirst().getId();
    }

    @Test
    void shouldReturnAllJobs() {
        RestAssured
                .when()
                .get("/api/job")
                .then()
                .statusCode(200)
                .body("size()", is(2)) // Assuming 2 jobs are inserted
                .body("[0].title", is("Developer"))
                .body("[1].title", is("Analyst"));
    }

    @Test
    void shouldReturnJobById() {
        RestAssured
                .when()
                .get("/api/job/" + firstId) // Assuming job with ID 1 exists in test data
                .then()
                .statusCode(200)
                .body("id", is(firstId.intValue()))
                .body("title", is("Developer"))
                .body("description", is("Backend Developer"))
                .body("company", is("Tech Corp"))
                .body("location", is("Berlin"));
    }

    @Test
    void shouldReturn404ForNonExistentJobId() {
        RestAssured
                .when()
                .get("/api/job/9999") // Assuming 9999 does not exist
                .then()
                .statusCode(404)
                .body("message", containsString("Job with id 9999 not found"));
    }
}
