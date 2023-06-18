package at.qe.backend.configs;

import at.qe.backend.configs.security.jwt.AuthEntryPointJwt;
import at.qe.backend.configs.security.jwt.AuthTokenFilter;
import at.qe.backend.configs.security.services.UserDetailsServiceImpl;
import at.qe.backend.models.UserRole;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    DataSource dataSource;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        try {
            http.cors().and().csrf().disable()
//                    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                    .authorizeHttpRequests(authorize -> authorize.requestMatchers("/emitter").permitAll()).httpBasic().and()
                    .authorizeHttpRequests(authorize -> authorize.requestMatchers("/emitter2").permitAll()).httpBasic().and()
                    .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**").authenticated()).httpBasic().and()
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/api/**").permitAll()
                            .requestMatchers("/error/**").permitAll()
                            .requestMatchers("/login/**").permitAll()
                            .requestMatchers("/logout/**").permitAll()
                            .requestMatchers("/logout-user/**").permitAll()
                            .requestMatchers("/user/**").permitAll()
                            .requestMatchers("/refreshtoken/**").permitAll()
                            .requestMatchers("/admin/**").permitAll()
                            .requestMatchers("/gardener/**").permitAll()
                            .requestMatchers("/upload/**").permitAll()
                            .requestMatchers("/greenhouse/**").permitAll()
                            .anyRequest().authenticated());
            http.authenticationProvider(authenticationProvider(userDetailsService));
            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        } catch (Exception ex) {
            throw new BeanCreationException("Wrong spring security configuration", ex);
        }
        // :TODO: user failureUrl(/login.xhtml?error) and make sure that a corresponding message is displayed
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}