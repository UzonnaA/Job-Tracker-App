package uzonna.JobTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    // Basic security config: allows all requests and disables CSRF for development
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // For dev only — re-enable for production
            .authorizeHttpRequests()
            .anyRequest().permitAll();

        return http.build();
    }
}
