package uzonna.JobTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Basic Spring Security configuration.
 * Currently allows all requests and disables CSRF protection for development simplicity.
 * 
 * NOTE: This setup is insecure for production and should be modified before deployment.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain.
     * - Disables CSRF protection to simplify development (like allowing POST from frontend).
     * - Permits all incoming requests without authentication.
     *
     * @param http the HttpSecurity instance used to configure security settings
     * @return the built SecurityFilterChain
     * @throws Exception if an error occurs while building the security chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for easier local development (not safe for production)
            .authorizeHttpRequests()
            .anyRequest().permitAll(); // Permit all requests with no authentication

        return http.build();
    }
}

