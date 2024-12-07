package de.abubeker.microapply.application.integration;

import de.abubeker.microapply.application.model.Application;
import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class ApplicationDeleteTests extends BaseIntegrationTest {
    @Autowired
    private TestDataUtil testDataUtil;

    private List<Long> applicationIds;

    @BeforeEach
    void setup() {
        super.setup();
        testDataUtil.deleteApplicationData();
        applicationIds = testDataUtil.insertApplicationData().stream().map(Application::getId).toList();
    }

    @Test
    void shouldDeleteApplicationById() {
        RestAssured
                .when()
                .get("/api/application/" + applicationIds.get(0)) // Fetch the first application
                .then()
                .statusCode(200);

        RestAssured
                .when()
                .delete("/api/application/" + applicationIds.get(0)) // Deleting the first application
                .then()
                .statusCode(204);

        RestAssured
                .when()
                .get("/api/application/" + applicationIds.get(0)) // Trying to get the deleted application
                .then()
                .statusCode(404)
                .body("message", containsString("Application with id " + applicationIds.get(0) + " not found"));
    }

    @Test
    void shouldReturn404ForNonExistentApplicationIdOnDelete() {
        RestAssured
                .when()
                .delete("/api/application/9999") // Assuming 9999 does not exist
                .then()
                .statusCode(404)
                .body("message", containsString("Application with id 9999 not found"));
    }
}
