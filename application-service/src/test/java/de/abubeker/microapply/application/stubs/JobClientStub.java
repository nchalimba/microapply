package de.abubeker.microapply.application.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class JobClientStub {

    /**
     * Stubs the JobClient's isJobAvailable call for a given job ID and response.
     *
     * @param jobId      The job ID to stub.
     * @param isValid    Whether the job is valid (true or false).
     */
    public static void stubIsJobAvailable(Long jobId, boolean isValid) {
        stubFor(get(urlMatching("/api/job/\\d+/validate"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"isValid\": " + isValid + "}")));
    }

    /**
     * Stubs the JobClient's isJobAvailable call to simulate a fallback due to failure.
     *
     * @param jobId The job ID to stub.
     */
    public static void stubIsJobAvailableFailure(Long jobId) {
        stubFor(get(urlEqualTo("/api/job/" + jobId.toString() + "/validate"))
                .willReturn(aResponse()
                        .withStatus(500))); // Simulates a failure, triggering fallback.
    }
}
