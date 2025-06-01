package uzonna.JobTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Configuration class to enable Cross-Origin Resource Sharing (CORS) for the application.
 * This allows the frontend (like React running on localhost:3000) to interact with the backend.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Defines CORS mappings for the entire application.
     * Allows requests from the specified origin (localhost:3000) with standard HTTP methods.
     *
     * @param registry the CorsRegistry used to register CORS configuration
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS settings to all endpoints
                .allowedOrigins("http://localhost:3000") // Allow frontend on port 3000
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Enable standard REST operations
                .allowedHeaders("*"); // Allow all headers
    }
}

