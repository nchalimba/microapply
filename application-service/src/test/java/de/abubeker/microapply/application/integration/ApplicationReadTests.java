package de.abubeker.microapply.application.integration;

import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class ApplicationReadTests extends BaseIntegrationTest {
    @Autowired
    private TestDataUtil testDataUtil;

    private Long firstApplicationId;

    @BeforeEach
    void setup() {
        super.setup();
        testDataUtil.deleteApplicationData();
        firstApplicationId = testDataUtil.insertApplicationData().get(0).getId();
    }

    @Test
    void shouldReturnAllApplications() {
        RestAssured
                .when()
                .get("/api/application")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].email", is("candidate1@example.com"))
                .body("[1].email", is("candidate2@example.com"));
    }

    @Test
    void shouldReturnApplicationById() {
        RestAssured
                .when()
                .get("/api/application/" + firstApplicationId) // Fetch the first application by ID
                .then()
                .statusCode(200)
                .body("id", is(firstApplicationId.intValue()))
                .body("email", is("candidate1@example.com"))
                .body("firstName", is("John"))
                .body("lastName", is("Doe"))
                .body("resumeUrl", is("http://example.com/resume1.pdf"));
    }

    @Test
    void shouldReturn404ForNonExistentApplicationId() {
        RestAssured
                .when()
                .get("/api/application/9999") // Query a non-existent ID
                .then()
                .statusCode(404)
                .body("message", containsString("Application with id 9999 not found"));
    }
}
