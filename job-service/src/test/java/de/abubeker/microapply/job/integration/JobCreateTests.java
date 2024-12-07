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
        super.setup();
        testDataUtil.deleteJobData();
    }

    @Test
    void shouldCreateJobSuccessfully() {
        String requestBody = "{ \"title\": \"Developer\", \"description\": \"Backend Developer\", \"company\": \"Tech Corp\", \"location\": \"Berlin\", \"status\": \"OPEN\" }";

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/job")
                .then()
                .statusCode(201)
                .body("title", is("Developer"))
                .body("description", is("Backend Developer"))
                .body("company", is("Tech Corp"))
                .body("location", is("Berlin"));
    }

    @Test
    void shouldReturn400ForInvalidJobData() {
        String requestBody = "{ \"description\": \"Backend Developer\", \"company\": \"Tech Corp\", \"location\": \"Berlin\", \"status\": \"OPEN\" }";

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/job")
                .then()
                .statusCode(400)
                .body("message", containsString("title: Title cannot be empty"));
    }

    @Test
    void shouldReturn400ForEmptyJobData() {
        String requestBody = "{ }"; // No fields provided

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/job")
                .then()
                .statusCode(400)
                .body("message", containsString("title: Title cannot be empty"));
    }
}
