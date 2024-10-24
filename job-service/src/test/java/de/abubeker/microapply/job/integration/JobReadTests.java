package de.abubeker.microapply.job.integration;

import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class JobReadTests extends BaseIntegrationTest{
    @Autowired
    private TestDataUtil testDataUtil;

    @BeforeEach
    void setup() {
        super.setup();
        testDataUtil.deleteJobData();
        testDataUtil.insertJobData();
    }

    @Test
    void shouldReturnAllJobs() {
        RestAssured
                .when()
                .get("/api/job")
                .then()
                .statusCode(200)
                .body("size()", is(2)) // Assuming 2 jobs are inserted
                .body("[0].title", is("Developer")) // Assert properties of the first job
                .body("[1].title", is("Analyst")); // Assert properties of the second job
    }

    @Test
    void shouldReturnJobById() {
        RestAssured
                .when()
                .get("/api/job/1") // Assuming job with ID 1 exists in your test data
                .then()
                .statusCode(200)
                .body("id", is(1)) // Assert the ID
                .body("title", is("Developer")) // Assert the title
                .body("description", is("Backend Developer")) // Assert the description
                .body("company", is("Tech Corp")) // Assert the company
                .body("location", is("Berlin")); // Assert the location
    }

    @Test
    void shouldReturn404ForNonExistentJobId() {
        RestAssured
                .when()
                .get("/api/job/9999") // Assuming 9999 does not exist
                .then()
                .statusCode(404) // Assert that the response status is 404
                .body("message", containsString("Job with id 9999 not found")); // Assert error message (make sure to adjust according to your actual error response structure)
    }
}
