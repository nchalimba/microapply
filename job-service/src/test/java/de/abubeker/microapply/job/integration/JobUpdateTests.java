package de.abubeker.microapply.job.integration;

import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;

public class JobUpdateTests extends BaseIntegrationTest {
    @Autowired
    private TestDataUtil testDataUtil;
    private Long firstId;


    @BeforeEach
    void setup() {
        super.setup(); // Set up the base configuration
        testDataUtil.deleteJobData(); // Clear existing job data
        firstId = testDataUtil.insertJobData().getFirst().getId();
    }

    @Test
    void shouldUpdateJobSuccessfully() {
        // Job update request body
        String requestBody = "{ \"title\": \"Senior Developer\", \"description\": \"Lead Backend Developer\", \"company\": \"Tech Corp\", \"location\": \"Berlin\", \"status\": \"OPEN\" }";

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/job/" + firstId) // API endpoint for updating a job with ID 1
                .then()
                .statusCode(200) // Assert that the response status is 200 OK
                .body("id", is(firstId.intValue())) // Assert the ID remains the same
                .body("title", is("Senior Developer")) // Assert the updated title
                .body("description", is("Lead Backend Developer")) // Assert the updated description
                .body("company", is("Tech Corp")) // Assert the company remains the same
                .body("location", is("Berlin")); // Assert the location remains the same
    }

    @Test
    void shouldReturn404ForNonExistentJobId() {
        // Job update request for a non-existent job ID
        String requestBody = "{ \"title\": \"Senior Developer\", \"description\": \"Lead Backend Developer\", \"company\": \"Tech Corp\", \"location\": \"Berlin\", \"status\": \"OPEN\" }";

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/job/9999") // Assuming job ID 9999 does not exist
                .then()
                .statusCode(404) // Assert that the response status is 404 Not Found
                .body("message", containsString("Job with id 9999 not found")); // Assert error message
    }

    @Test
    void shouldReturn400ForInvalidJobData() {
        // Invalid job update request (missing title)
        String requestBody = "{ \"description\": \"Lead Backend Developer\", \"company\": \"Tech Corp\", \"location\": \"Berlin\", \"status\": \"OPEN\" }";

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/job/1") // Update job with ID 1
                .then()
                .statusCode(400) // Assert that the response status is 400 Bad Request
                .body("message", containsString("title: Title cannot be empty")); // Assert error message
    }
}
