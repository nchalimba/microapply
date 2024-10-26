package de.abubeker.microapply.gateway.routes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RoutesTest {

    @Autowired
    private RouterFunction<ServerResponse> jobServiceRoute;

    @Autowired
    private RouterFunction<ServerResponse> jobServiceSwaggerRoute;

    @Autowired
    private RouterFunction<ServerResponse> applicationServiceRoute;

    @Autowired
    private RouterFunction<ServerResponse> applicationServiceSwaggerRoute;

    @Autowired
    private RouterFunction<ServerResponse> fallbackRoute;

    @Test
    public void testJobServiceRoute() {
        assertThat(jobServiceRoute).isNotNull();
    }

    @Test
    public void testJobServiceSwaggerRoute() {
        assertThat(jobServiceSwaggerRoute).isNotNull();
    }

    @Test
    public void testApplicationServiceRoute() {
        assertThat(applicationServiceRoute).isNotNull();
    }

    @Test
    public void testApplicationServiceSwaggerRoute() {
        assertThat(applicationServiceSwaggerRoute).isNotNull();
    }

    @Test
    public void testFallbackRoute() {
        assertThat(fallbackRoute).isNotNull();
    }
}
