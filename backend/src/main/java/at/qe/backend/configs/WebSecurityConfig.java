package at.qe.backend.configs;

import at.qe.backend.configs.security.jwt.AuthEntryPointJwt;
import at.qe.backend.configs.security.jwt.AuthTokenFilter;
import at.qe.backend.configs.security.services.UserDetailsServiceImpl;
import at.qe.backend.models.UserRole;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig {
    private static final String ADMIN = UserRole.ADMIN.name();
    private static final String GARDENER = UserRole.GARDENER.name();
    private static final String USER = UserRole.USER.name();

    @Autowired
    DataSource dataSource;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        try {
            http.cors().and().csrf().disable()
                    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**").authenticated()).httpBasic().and()
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/api/**").permitAll()
                            .requestMatchers("/jakarta.faces.resource/**").permitAll()
                            .requestMatchers("/error/**").permitAll()
                            .requestMatchers("/login/**").permitAll()
                            .requestMatchers("/logout-user/**").permitAll()
                            .requestMatchers("/user/**").permitAll()
                            .requestMatchers("/refresh/**").permitAll()
                            .requestMatchers("/admin/**").permitAll()
                            .requestMatchers("/secured/**").hasAnyAuthority(ADMIN, GARDENER, USER)
                            .requestMatchers("/omnifaces.push/**").hasAnyAuthority(ADMIN, GARDENER, USER)
                            .anyRequest().authenticated());
            http.authenticationProvider(authenticationProvider());
            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        } catch (Exception ex) {
            throw new BeanCreationException("Wrong spring security configuration", ex);
        }
        // :TODO: user failureUrl(/login.xhtml?error) and make sure that a corresponding message is displayed
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        //Configure roles and passwords via datasource
//        auth.
//                jdbcAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("select username, password, enabled from userx where username=?")
//                .authoritiesByUsernameQuery("select u.username, ur.roles from userx u join userx_user_role ur on u.id=ur.userx_id where u.username=?")
//                .dataSource(dataSource);
//    }
//

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}