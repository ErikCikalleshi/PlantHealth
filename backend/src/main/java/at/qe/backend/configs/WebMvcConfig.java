package at.qe.backend.configs;

import at.qe.backend.components.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthInterceptor authorizationInterceptor;

    public WebMvcConfig(AuthInterceptor authorizationInterceptor) {
        this.authorizationInterceptor = authorizationInterceptor;
    }
    /**
     * Adds a custom interceptor for '/user' endpoint
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/user");
    }
}
