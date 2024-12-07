package de.abubeker.microapply.gateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SecurityConfigTest {

    private final SecurityConfig securityConfig = new SecurityConfig();

    @Test
    void corsConfigurationSource_ShouldReturnProperConfiguration() {
        // When
        CorsConfigurationSource corsConfigurationSource = securityConfig.corsConfigurationSource();
        CorsConfiguration corsConfiguration = corsConfigurationSource.getCorsConfiguration(null);

        // Then
        assertNotNull(corsConfiguration);
        assertTrue(corsConfiguration.getAllowedMethods().containsAll(
                java.util.Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")));
        assertNotNull(corsConfiguration.getAllowedHeaders());
        assertNotNull(corsConfiguration.getAllowedOrigins());
    }

    @Test
    void securityFilterChain_ShouldConfigureSecurityCorrectly() throws Exception {
        // Given
        HttpSecurity httpSecurity = mock(HttpSecurity.class);

        // When
        SecurityFilterChain filterChain = securityConfig.securityFilterChain(httpSecurity);

        // Then
        assertNotNull(filterChain);
    }

    @Test
    void freeResourceUrls_ShouldContainExpectedPaths() {
        // Given
        String[] expectedUrls = {
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/api-docs/**",
                "/aggregate/**",
                "/actuator/prometheus"
        };

        // When/Then
        assertArrayEquals(expectedUrls, securityConfig.freeResourceUrls);
    }
}