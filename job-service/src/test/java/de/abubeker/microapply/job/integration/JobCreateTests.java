package de.abubeker.microapply.job.integration;

import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class JobCreateTests extends BaseIntegrationTest {

    @Autowired
    private TestDataUtil testDataUtil;

    @BeforeEach
    void setup() {
        super.setup(); // Set up the base configuration
        testDataUtil.deleteJobData(); // Clear existing job data
    }

    @Test
    void shouldCreateJobSuccessfully() {
        // Job creation request body
        String requestBody = "{ \"title\": \"Developer\", \"description\": \"Backend Developer\", \"company\": \"Tech Corp\", \"location\": \"Berlin\" }";

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/job") // API endpoint for creating a job
                .then()
                .statusCode(201) // Assert that the response status is 201 Created
                .body("title", is("Developer")) // Assert the title in the response
                .body("description", is("Backend Developer")) // Assert the description
                .body("company", is("Tech Corp")) // Assert the company
                .body("location", is("Berlin")); // Assert the location
    }

    @Test
    void shouldReturn400ForInvalidJobData() {
        // Invalid job creation request (title is missing)
        String requestBody = "{ \"description\": \"Backend Developer\", \"company\": \"Tech Corp\", \"location\": \"Berlin\" }";

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/job")
                .then()
                .statusCode(400) // Assert that the response status is 400 Bad Request
                .body("message", containsString("title: Title cannot be empty")); // Assert error message (adjust according to your error response structure)
    }

    @Test
    void shouldReturn400ForEmptyJobData() {
        // Empty job creation request
        String requestBody = "{ }"; // No fields provided

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/job")
                .then()
                .statusCode(400) // Assert that the response status is 400 Bad Request
                .body("message", containsString("title: Title cannot be empty")); // Assert error message
    }
}
