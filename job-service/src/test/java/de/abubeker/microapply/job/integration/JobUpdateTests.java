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
        String requestBody = "{ \"title\": \"Senior Developer\", \"description\": \"Lead Backend Developer\", \"company\": \"Tech Corp\", \"location\": \"Berlin\", \"status\": \"OPEN\" }";

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/job/" + firstId)
                .then()
                .statusCode(200)
                .body("id", is(firstId.intValue()))
                .body("title", is("Senior Developer"))
                .body("description", is("Lead Backend Developer"))
                .body("company", is("Tech Corp"))
                .body("location", is("Berlin"));
    }

    @Test
    void shouldReturn404ForNonExistentJobId() {
        String requestBody = "{ \"title\": \"Senior Developer\", \"description\": \"Lead Backend Developer\", \"company\": \"Tech Corp\", \"location\": \"Berlin\", \"status\": \"OPEN\" }";

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/job/9999") // Assuming job ID 9999 does not exist
                .then()
                .statusCode(404)
                .body("message", containsString("Job with id 9999 not found"));
    }

    @Test
    void shouldReturn400ForInvalidJobData() {
        String requestBody = "{ \"description\": \"Lead Backend Developer\", \"company\": \"Tech Corp\", \"location\": \"Berlin\", \"status\": \"OPEN\" }";

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/job/1")
                .then()
                .statusCode(400)
                .body("message", containsString("title: Title cannot be empty"));
    }
}
