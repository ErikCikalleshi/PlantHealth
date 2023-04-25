package at.qe.backend.configs;

//import at.qe.backend.components.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOriginPatterns("http://127.0.0.1:5173")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true); // Set the preflight request cache duration
    }
}
