package de.abubeker.microapply.application.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import de.abubeker.microapply.application.stubs.JobClientStub;
import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
@AutoConfigureWireMock(port = 0)
public class ApplicationCreateTests extends BaseIntegrationTest {

    @Autowired
    private TestDataUtil testDataUtil;

    @BeforeEach
    void setup() {
        super.setup();
        testDataUtil.deleteApplicationData();
        WireMock.reset();
    }

    @Test
    void shouldCreateApplicationSuccessfully() {
        JobClientStub.stubIsJobAvailable(101L, true);

        String requestBody = """
        {
            "jobId": 101,
            "userId": 201,
            "status": "SUBMITTED",
            "resumeUrl": "http://example.com/resume1.pdf",
            "coverLetterUrl": "http://example.com/cover1.pdf",
            "email": "candidate1@example.com",
            "firstName": "John",
            "lastName": "Doe",
            "phoneNumber": "1234567890"
        }
        """;

        var response = RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/application")
                .then()
                .extract()
                .body().asString();
        System.out.println(response);
        // TODO: fix test
//                .body("email", is("candidate1@example.com")) // Verify email
//                .body("firstName", is("John")) // Verify first name
//                .body("lastName", is("Doe")) // Verify last name
//                .body("resumeUrl", is("http://example.com/resume1.pdf")); // Verify resume URL

        verify(getRequestedFor(urlEqualTo("/api/job/101/validate"))); // Verify WireMock received the correct request
    }

    @Test
    void shouldReturn400ForInvalidJob() {
        JobClientStub.stubIsJobAvailable(999L, false);

        String requestBody = """
        {
            "jobId": 999,
            "userId": 202,
            "status": "SUBMITTED",
            "resumeUrl": "http://example.com/resume2.pdf",
            "coverLetterUrl": "http://example.com/cover2.pdf",
            "email": "candidate2@example.com",
            "firstName": "Jane",
            "lastName": "Smith",
            "phoneNumber": "0987654321"
        }
        """;

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/application")
                .then()
                .statusCode(400)
                .body("message", containsString("Job with id 999 is not available"));
    }

    @Test
    void shouldReturn400ForMissingRequiredFields() {
        JobClientStub.stubIsJobAvailable(101L, true);

        String requestBody = """
        {
            "jobId": 101,
            "userId": 201,
            "status": "SUBMITTED",
            "resumeUrl": "http://example.com/resume1.pdf",
            "coverLetterUrl": "http://example.com/cover1.pdf",
            "lastName": "Doe",
            "phoneNumber": "1234567890"
        }
        """;

        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/application")
                .then()
                .statusCode(400)
                .body("message", containsString("email: Email cannot be empty"))
                .body("message", containsString("firstName: First name cannot be empty"));
    }
}
