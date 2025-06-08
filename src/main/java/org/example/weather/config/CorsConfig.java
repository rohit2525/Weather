package org.example.weather.config;

import java.util.Arrays; // Import Arrays
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(false); // Must be false when allowedOrigins is "*"
    config.setAllowedOrigins(List.of("*")); // Allow all origins (WARNING: Security Risk)
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*")); // Allow all headers

    source.registerCorsConfiguration("/**", config); // Apply to all paths
    return new CorsFilter(source);
  }
}
