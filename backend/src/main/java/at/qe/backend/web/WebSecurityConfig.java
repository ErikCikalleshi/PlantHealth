package at.qe.backend.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

//        http
//                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**").authenticated()).httpBasic()
//                .and()
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/").permitAll()
//                        //.requestMatchers("/api/**").permitAll()
//                        .requestMatchers("/**.jsf").permitAll()
//                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
//                        .requestMatchers("/jakarta.faces.resource/**").permitAll()
//                        .requestMatchers("/error/**").permitAll()
//                        .requestMatchers("/admin/**").hasAnyAuthority(ADMIN)
//                        .requestMatchers("/secured/**").hasAnyAuthority(ADMIN, MANAGER, EMPLOYEE)
//                        .requestMatchers("/omnifaces.push/**").hasAnyAuthority(ADMIN, MANAGER, EMPLOYEE)
//                        .anyRequest().authenticated())
//                .formLogin()
//                .loginPage("/login.xhtml")
//                .permitAll()
//                .failureUrl("/error/access_denied.xhtml")
//                .defaultSuccessUrl("/secured/welcome.xhtml")
//                .loginProcessingUrl("/login")
//                .successForwardUrl("/secured/welcome.xhtml")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login.xhtml")
//                .deleteCookies("JSESSIONID");
//
        return http.build();
    }
}

