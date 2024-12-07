package de.abubeker.microapply.application.integration;

import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class ApplicationUpdateTests extends BaseIntegrationTest {
    @Autowired
    private TestDataUtil testDataUtil;

    private Long firstId;

    @BeforeEach
    void setup() {
        super.setup();
        testDataUtil.deleteApplicationData();
        firstId = testDataUtil.insertApplicationData().get(0).getId();
    }

    @Test
    void shouldUpdateApplicationSuccessfully() {
        String requestBody = """
                {
                    "jobId": 202,
                    "userId": 303,
                    "status": "REVIEWED",
                    "applicationDate": "2024-11-10T10:00:00",
                    "resumeUrl": "http://example.com/updated_resume.pdf",
                    "coverLetterUrl": "http://example.com/updated_cover.pdf",
                    "notes": "Updated notes for the candidate.",
                    "email": "updated.email@example.com",
                    "firstName": "UpdatedFirstName",
                    "lastName": "UpdatedLastName",
                    "phoneNumber": "5555555555"
                }
                """;

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/application/" + firstId) // API endpoint for updating an application
                .then()
                .statusCode(200)
                .body("id", is(firstId.intValue()))
                .body("status", is("REVIEWED"))
                .body("resumeUrl", is("http://example.com/updated_resume.pdf"))
                .body("email", is("updated.email@example.com"))
                .body("firstName", is("UpdatedFirstName"))
                .body("lastName", is("UpdatedLastName"))
                .body("phoneNumber", is("5555555555"));
    }

    @Test
    void shouldReturn404ForNonExistentApplicationId() {
        String requestBody = """
                {
                    "jobId": 202,
                    "userId": 303,
                    "status": "REVIEWED",
                    "applicationDate": "2024-11-10T10:00:00",
                    "resumeUrl": "http://example.com/updated_resume.pdf",
                    "coverLetterUrl": "http://example.com/updated_cover.pdf",
                    "notes": "Updated notes for the candidate.",
                    "email": "updated.email@example.com",
                    "firstName": "UpdatedFirstName",
                    "lastName": "UpdatedLastName",
                    "phoneNumber": "5555555555"
                }
                """;

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/application/9999") // Assuming application ID 9999 does not exist
                .then()
                .statusCode(404)
                .body("message", containsString("Application with id 9999 not found"));
    }

    @Test
    void shouldReturn400ForInvalidApplicationData() {
        String requestBody = """
                {
                    "jobId": 202,
                    "userId": 303,
                    "status": "REVIEWED",
                    "applicationDate": "2024-11-10T10:00:00",
                    "resumeUrl": "http://example.com/updated_resume.pdf",
                    "coverLetterUrl": "http://example.com/updated_cover.pdf",
                    "notes": "Updated notes for the candidate.",
                    "firstName": "UpdatedFirstName",
                    "lastName": "UpdatedLastName",
                    "phoneNumber": "5555555555"
                }
                """;

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/application/" + firstId) // Update application with missing email
                .then()
                .statusCode(400)
                .body("message", containsString("email: Email cannot be empty"));
    }
}
