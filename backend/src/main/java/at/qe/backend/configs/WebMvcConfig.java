package at.qe.backend.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOriginPatterns("http://127.0.0.1:5173", "http://localhost:5173", "http://172.25.2.206:5173")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true); // Set the preflight request cache duration
    }
}
